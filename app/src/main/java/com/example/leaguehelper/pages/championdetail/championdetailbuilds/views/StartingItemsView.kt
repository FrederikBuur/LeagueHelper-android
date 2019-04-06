package com.example.leaguehelper.pages.championdetail.championdetailbuilds.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.example.leaguehelper.R
import com.example.leaguehelper.data.SessionController
import com.example.leaguehelper.models.championgg.ChampData
import com.example.leaguehelper.models.staticdata.Image
import com.example.leaguehelper.util.ImageLoader
import kotlinx.android.synthetic.main.view_starting_items.view.*
import java.util.*

class StartingItemsView : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        View.inflate(context, R.layout.view_starting_items, this)
    }

    fun setupView(champData: ChampData, showMostFrequent: Boolean) {
        val version = SessionController.getInstance().configData?.version
        val items = if (showMostFrequent) {
            val temp = champData.hashes.firstitemshash.highestCount.hash.split("-")
            temp.subList(1, temp.size)
        } else {
            val temp = champData.hashes.firstitemshash.highestWinrate.hash.split("-")
            temp.subList(1, temp.size)
        }
        version?.let { v ->
            when (items.size) {
                1 -> {
                    val item1Url = Image.getItemIconPath(v, items[0])
                    ImageLoader.loadImage(context, item1Url, item1)
                }
                2 -> {
                    val item1Url = Image.getItemIconPath(v, items[0])
                    ImageLoader.loadImage(context, item1Url, item1)
                    val item2Url = Image.getItemIconPath(v, items[1])
                    ImageLoader.loadImage(context, item2Url, item2)
                }
                3 -> {
                    val item1Url = Image.getItemIconPath(v, items[0])
                    ImageLoader.loadImage(context, item1Url, item1)
                    val item2Url = Image.getItemIconPath(v, items[1])
                    ImageLoader.loadImage(context, item2Url, item2)
                    val item3Url = Image.getItemIconPath(v, items[2])
                    ImageLoader.loadImage(context, item3Url, item3)
                }
                else -> {

                }
            }
        }
    }

}