package com.example.leaguehelper.pages.championdetail.championdetailbuilds.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.example.leaguehelper.R
import com.example.leaguehelper.models.championgg.ChampData

class SummonerSpellsView : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        View.inflate(context, R.layout.view_summoner_spells, this)
    }

    fun setupView(champData: ChampData) {

    }

}