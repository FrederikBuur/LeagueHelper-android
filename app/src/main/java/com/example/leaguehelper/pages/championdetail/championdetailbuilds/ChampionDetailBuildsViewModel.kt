package com.example.leaguehelper.pages.championdetail.championdetailbuilds

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class ChampionDetailBuildsViewModel(application: Application) : AndroidViewModel(application) {

    private var championDetailBuildsRepository =
        ChampionDetailBuildsRepository(application)

}