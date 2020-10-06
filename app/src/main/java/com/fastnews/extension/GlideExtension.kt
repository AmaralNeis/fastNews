package com.fastnews.extension

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.RequestManager

fun RequestManager.loadWith(url: String, imageView: ImageView) {

    this.load(url)
        .placeholder(ColorDrawable(Color.LTGRAY))
        .centerCrop()
        .error(ColorDrawable(Color.DKGRAY))
        .into(imageView)

    imageView.visibility = View.VISIBLE
}