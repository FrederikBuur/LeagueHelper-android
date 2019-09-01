package com.example.leaguehelper.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.leaguehelper.R
import kotlin.math.roundToInt

object ImageLoader {

    fun loadImage(context: Context, imageSrc: String, imageView: ImageView, progressView: ProgressBar? = null) {

        Glide.with(context)
            .load(imageSrc)
            .apply(
                RequestOptions.centerCropTransform()
                    .placeholder(R.drawable.ic_account)
                    .error(R.drawable.ic_error)
            )
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                    @Suppress("SENSELESS_COMPARISON")
                    if (imageView == null) return false
                    imageView.visibility = View.VISIBLE
                    progressView?.visibility = View.INVISIBLE
                    return false
                }

                override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                    @Suppress("SENSELESS_COMPARISON")
                    if (imageView == null) return false
                    imageView.visibility = View.VISIBLE
                    progressView?.visibility = View.INVISIBLE
                    return false
                }
            })
            .into(imageView)
    }

}