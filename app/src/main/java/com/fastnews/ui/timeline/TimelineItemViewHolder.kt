package com.fastnews.ui.timeline

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.fastnews.R
import com.fastnews.extension.loadImageWith
import com.fastnews.mechanism.TimeElapsed
import com.fastnews.model.PostData
import kotlinx.android.synthetic.main.include_item_timeline_ic_comments.view.*
import kotlinx.android.synthetic.main.include_item_timeline_ic_score.view.*
import kotlinx.android.synthetic.main.include_item_timeline_thumbnail.view.*
import kotlinx.android.synthetic.main.include_item_timeline_timeleft.view.*
import kotlinx.android.synthetic.main.include_item_timeline_title.view.*
import kotlinx.android.synthetic.main.item_timeline.view.*

class TimelineItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(parent: ViewGroup): TimelineItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_timeline, parent, false)
            return TimelineItemViewHolder(view)
        }
    }

    fun bind(value: PostData, onClickItem: (PostData, ImageView) -> Unit, onShareListener: (String?) -> Unit) {
        view.setOnClickListener { onClickItem(value, view.item_timeline_thumbnail) }
        populateAuthor(value)
        populateTime(value)
        populateThumbnail(value)
        populateTitle(value)
        populateScore(value)
        populateComments(value)
        setupSharedClick(value.url, onShareListener)
    }

    private fun setupSharedClick(url: String?, onShareListener: (String?) -> Unit) {
        view.include_item_timeline_ic_share.setOnClickListener {
            onShareListener(url)
        }
    }

    private fun populateComments(value: PostData?) {
        value?.numComments.let {
            view.item_timeline_bt_comments_text.text = it.toString()
        }
    }

    private fun populateScore(value: PostData?) {
        value?.score.let {
            view.item_timeline_bt_score_text.text = it.toString()
        }
    }

    private fun populateTitle(value: PostData?) {
        value?.title.let {
            view.item_timeline_title.text = it
        }
    }

    private fun populateThumbnail(value: PostData?) {

        value?.imageUrl?.let {
            val PREFIX_HTTP = "http"
            if (!TextUtils.isEmpty(it) && it.startsWith(PREFIX_HTTP)) {
                val url = HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
                view.item_timeline_thumbnail.loadImageWith(url)
            } else {
                view.item_timeline_thumbnail.visibility = View.GONE
            }
        }?: kotlin.run { view.item_timeline_thumbnail.visibility = View.GONE }
    }

    private fun populateTime(value: PostData?) {
        value?.createdUtc.let {
            val elapsed = TimeElapsed.getTimeElapsed(it!!)
            view.item_timeline_timeleft.text = elapsed
        }
    }

    private fun populateAuthor(value: PostData?) {
        value?.author.let {
            view.item_timeline_author.text = it
        }
    }

}