package com.fastnews.extension

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImageWith(url: String) {
    Glide.with(this).loadWith(url, this)
}
