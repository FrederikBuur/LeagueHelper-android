package com.example.leaguehelper.pages.championdetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.leaguehelper.data.LeagueHelperDatabase
import com.example.leaguehelper.models.staticdata.champion.Champion
import com.example.leaguehelper.pages.champions.ChampionsRepository
import com.example.leaguehelper.pages.main.MainRepository

class ChampionDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: ChampionDetailRepository


    init {
        val championDao = LeagueHelperDatabase.getInstance(application).championDao()
        repo = ChampionDetailRepository(championDao)
    }

    fun getChampions(championId: String) = repo.getChampion(championId)
}