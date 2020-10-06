package com.fastnews.ui.timeline

import androidx.recyclerview.widget.DiffUtil
import com.fastnews.service.model.PostData

class DiffUtilCallBack : DiffUtil.ItemCallback<PostData>() {

    override fun areContentsTheSame(oldItem: PostData, newItem: PostData) = oldItem == newItem

    override fun areItemsTheSame(oldItem: PostData, newItem: PostData) = oldItem.id == newItem.id

}