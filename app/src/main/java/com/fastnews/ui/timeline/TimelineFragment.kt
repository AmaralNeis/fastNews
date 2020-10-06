package com.fastnews.ui.timeline

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.fastnews.R
import com.fastnews.mechanism.VerifyNetworkInfo
import com.fastnews.service.model.PostData
import com.fastnews.ui.detail.DetailFragment.Companion.KEY_POST
import com.fastnews.viewmodel.PostViewModel
import kotlinx.android.synthetic.main.fragment_timeline.*
import kotlinx.android.synthetic.main.include_state_without_conn_timeline.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs


class TimelineFragment : Fragment(R.layout.fragment_timeline) {

    private val viewModel: PostViewModel by viewModel ()

    private val adapter: TimelineAdapter by lazy {
        TimelineAdapter( onClickItem = {it, imageView -> onClickItem(it, imageView)},
            onShareListener = {onShareListenerWith(it)})
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            buildActionBar()
            buildTimeline()
            verifyConnectionState()
    }

    private fun verifyConnectionState() {
        context.let {
            if (VerifyNetworkInfo.isConnected(it!!)) {
                hideNoConnectionState()
                showProgress()
                fetchTimeline()
            } else {
                hideProgress()
                showNoConnectionState()

                state_without_conn_timeline.setOnClickListener {
                    verifyConnectionState()
                }
            }
        }
    }

    private fun buildActionBar() {
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(false) // disable the button
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false) // remove the left caret
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.title = resources.getString(R.string.app_name)
    }

    private fun buildTimeline() {
        timeline_rv.layoutManager = LinearLayoutManager(context)
        timeline_rv.itemAnimator = DefaultItemAnimator()
        timeline_rv.adapter = adapter

    }

    private fun fetchTimeline() {
        if (adapter.itemCount == 0) {
            lifecycleScope.launch {
                viewModel.getPosts().collectLatest {
                    adapter.submitData(it)
                }
            }
        }

        hideProgress()
        showPosts()
    }

    private fun showPosts() {
            timeline_rv.visibility = View.VISIBLE
    }

    private fun showProgress() {
        state_progress_timeline.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        state_progress_timeline.visibility = View.GONE
    }

    private fun showNoConnectionState() {
        state_without_conn_timeline.visibility = View.VISIBLE
    }

    private fun hideNoConnectionState() {
        state_without_conn_timeline.visibility = View.GONE
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