package com.fastnews.ui.timeline

import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import com.fastnews.model.PostData

class TimelineAdapter(val onClickItem: (PostData, ImageView) -> Unit, val onShareListener: (String?) -> Unit): PagingDataAdapter<PostData, TimelineItemViewHolder>(DiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TimelineItemViewHolder.create(parent)

    override fun onBindViewHolder(holder: TimelineItemViewHolder, position: Int) {
        getItem(position)?.let {post ->
            holder.bind(post, onClickItem, onShareListener)
        }
    }
}