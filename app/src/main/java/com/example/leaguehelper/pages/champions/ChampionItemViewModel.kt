package com.example.leaguehelper.pages.champions

import android.view.View
import com.example.leaguehelper.models.staticdata.champion.Champion

data class ChampionItemViewModel (val champion: Champion,
                                  val onChampionClicked: (Champion, View) -> Unit) {

    fun onChampionClicked(v: View) {
        onChampionClicked.invoke(champion, v)
    }

}