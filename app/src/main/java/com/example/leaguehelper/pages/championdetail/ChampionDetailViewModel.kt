package com.example.leaguehelper.pages.championdetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.leaguehelper.models.staticdata.champion.Champion
import com.example.leaguehelper.pages.champions.ChampionsRepository

class ChampionDetailViewModel(application: Application) : AndroidViewModel(application) {

    private var championDetailRepository = ChampionDetailRepository(application)


    fun getChampions(championId: String): LiveData<Champion> {
        return championDetailRepository.getChampion(championId)
    }
}