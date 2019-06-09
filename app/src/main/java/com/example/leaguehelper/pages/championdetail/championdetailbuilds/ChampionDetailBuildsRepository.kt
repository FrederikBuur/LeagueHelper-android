package com.example.leaguehelper.pages.championdetail.championdetailbuilds

import android.app.Application
import com.example.leaguehelper.data.LeagueHelperDatabase

class ChampionDetailBuildsRepository(val application: Application) {

    init {
        val database = LeagueHelperDatabase.getInstance(application)
    }

    companion object {
        const val TAG = "ChampDetailBuildsRepo"
    }
}