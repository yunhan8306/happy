package com.example.happy.common.util

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.example.happy.R

fun ImageView.setImage(
    url: String?,
    @DrawableRes placeholder: Int = R.drawable.ic_launcher_foreground,
    @DrawableRes error: Int? = R.drawable.ic_launcher_background
) {
    Glide.with(context)
        .load(url)
        .timeout(60000)
        .centerCrop()
        .placeholder(placeholder)
        .error(error)
        .into(this)
}