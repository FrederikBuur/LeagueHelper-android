package com.example.leaguehelper.pages.champions

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import androidx.navigation.findNavController
import com.example.leaguehelper.R
import com.example.leaguehelper.data.SessionController
import com.example.leaguehelper.models.staticdata.Image
import com.example.leaguehelper.models.staticdata.champion.Champion
import com.example.leaguehelper.util.ImageLoader
import kotlinx.android.synthetic.main.view_champions_champion.view.*

class ChampionsChampionView(context: Context) : FrameLayout(context) {

    init {
        View.inflate(context, R.layout.view_champions_champion, this)
    }

    fun setupView(champion: Champion) {

        championSmallTitle.text = champion.name
        val version = SessionController.getInstance().configData?.version ?: context.getString(R.string.fallback_version)
        val championImageUrl = Image.getChampionIconPath(version, champion.image.full)
        ImageLoader.loadImage(context, championImageUrl, championSmallImage)

        championSmallContainer.setOnClickListener {
            navigateToDetail(champion)
        }
    }

    private fun navigateToDetail(champion: Champion) {
        val id = champion.id
        val key = champion.key
        val action = ChampionsFragmentDirections.toChampionDetail(id, key)
        findNavController().navigate(action)
    }

}