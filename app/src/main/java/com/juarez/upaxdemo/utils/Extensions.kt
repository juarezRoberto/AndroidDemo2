package com.juarez.upaxdemo.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadPosterImage(posterPath: String) {
    Glide
        .with(this.context)
        .load("${Constants.IMG_BASE_URL}$posterPath")
        .into(this)
}