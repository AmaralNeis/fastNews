package com.fastnews.ui.timeline

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import com.fastnews.R
import com.fastnews.mechanism.VerifyNetworkInfo
import com.fastnews.service.model.PostData
import com.fastnews.ui.detail.DetailFragment.Companion.KEY_POST
import com.fastnews.viewmodel.PostViewModel
import kotlinx.android.synthetic.main.fragment_timeline.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TimelineFragment: Fragment(R.layout.fragment_timeline) {

    private val viewModel: PostViewModel by viewModel ()

    private val timelineAdapter: TimelineAdapter by lazy {
        TimelineAdapter( onClickItem = {it, imageView -> onClickItem(it, imageView)},
            onShareListener = {onShareListenerWith(it)})
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        buildActionBar()
        buildTimeline()
        setupSwipeRefresh()
        verifyConnectionState()
    }

    private fun verifyConnectionState() {
        context.let {
            if (VerifyNetworkInfo.isConnected(it!!)) {
//                hideNoConnectionState()
                showProgress()
                fetchTimeline()
            } else {
                hideProgress()
                showNoConnectionState()

//                state_without_conn_timeline.setOnClickListener {
//                    verifyConnectionState()
//                }
            }
        }
    }

    private fun buildActionBar() {

        (activity as AppCompatActivity).supportActionBar?.apply {
            setHomeButtonEnabled(false)
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
            title = resources.getString(R.string.app_name)
        }
    }

    private fun buildTimeline() {

        timeline_rv.apply {
            itemAnimator = DefaultItemAnimator()
            adapter = timelineAdapter
        }

        lifecycleScope.launch {
            timelineAdapter.loadStateFlow.collectLatest { loadStates ->
                 if (loadStates.refresh is LoadState.Loading) showProgress() else hideProgress()
            }
        }
    }

    private fun setupSwipeRefresh() {
        swipeRefresh.setOnRefreshListener {
            timelineAdapter.refresh()
            fetchTimeline(true)
        }
    }

    private fun fetchTimeline(isRefresh: Boolean = false) {
        if (timelineAdapter.itemCount == 0) {
            lifecycleScope.launch {

                viewModel.getPosts(isRefresh).collectLatest {
                    swipeRefresh.isRefreshing = false
                    timelineAdapter.submitData(it)
                }
            }
        }
    }

    private fun showProgress() {
        swipeRefresh.isRefreshing = true
        state_progress_timeline.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        swipeRefresh.isRefreshing = false
        state_progress_timeline.visibility = View.GONE
    }

    private fun showNoConnectionState() {
//        state_without_conn_timeline.visibility = View.VISIBLE
    }

    private fun onClickItem(postData: PostData, imageView: ImageView) {
        val extras = FragmentNavigatorExtras(
            imageView to "thumbnail"
        )
        val bundle = Bundle().apply {
            putParcelable(KEY_POST, postData)
        }
        findNavController().navigate(R.id.action_timeline_to_detail, bundle, null, extras)
    }

    private fun onShareListenerWith(url: String?) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }


}