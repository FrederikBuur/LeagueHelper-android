package com.example.leaguehelper.pages.championdetail.championdetailbuilds.views

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import com.example.leaguehelper.R
import com.example.leaguehelper.models.championgg.ChampData

class FinalBuildView(context: Context) : FrameLayout(context) {

    init {
        View.inflate(context, R.layout.view_starting_items, this)
    }

    fun setupView(champData: ChampData) {

    }

}