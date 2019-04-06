package com.example.leaguehelper.pages.championdetail.championdetailbuilds

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.leaguehelper.models.championgg.ChampData

class ChampionDetailBuildsViewModel(application: Application) : AndroidViewModel(application) {

    private var championDetailBuildsRepository =
        ChampionDetailBuildsRepository(application)

    fun getChampionBuilds(championIdKey: Int) : LiveData<List<ChampData?>> {
        return championDetailBuildsRepository.getChampData(championIdKey)
    }
}