package com.fastnews.ui.timeline

import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fastnews.service.model.PostData
import kotlinx.android.synthetic.main.include_item_timeline_thumbnail.view.*

class TimelineAdapter(val onClickItem: (PostData, ImageView) -> Unit, val onShareListener: (String?) -> Unit): PagingDataAdapter<PostData, TimelineItemViewHolder>(DiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TimelineItemViewHolder.create(parent)

    override fun onBindViewHolder(holder: TimelineItemViewHolder, position: Int) {
        getItem(position)?.let {post ->
            holder.bind(post, onClickItem, onShareListener)
        }
    }
}