package com.example.leaguehelper.util

import android.app.Activity
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.leaguehelper.R

fun ImageView.setImageUrl(url: String) {
    val con = context
    if (con is Activity && con.isDestroyed
        || con == null
    ) return

    Glide.with(context)
        .load(url)
        .apply(RequestOptions().placeholder(R.drawable.ic_account))
        .transition(GenericTransitionOptions.with<Drawable>(R.anim.fade_in))
        .disallowHardwareConfig()
        .into(this)
}