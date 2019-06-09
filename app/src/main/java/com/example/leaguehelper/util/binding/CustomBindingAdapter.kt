package com.example.leaguehelper.util.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.leaguehelper.util.ImageLoader

class CustomBindingAdapter {

    companion object {

        @JvmStatic
        @BindingAdapter("imageUrl")
        fun setImageUrl(imageView: ImageView, url: String?) {
            url?.let {
                ImageLoader.loadImage(imageView.context, url, imageView)
            }
        }
    }
}