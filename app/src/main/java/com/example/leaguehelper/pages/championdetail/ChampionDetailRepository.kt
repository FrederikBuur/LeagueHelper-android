package com.example.leaguehelper.pages.championdetail

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.leaguehelper.data.LeagueHelperDatabase
import com.example.leaguehelper.data.dao.ChampionDao
import com.example.leaguehelper.models.staticdata.champion.Champion

class ChampionDetailRepository(application: Application) {

    private var championDao: ChampionDao

    init {
        val database = LeagueHelperDatabase.getInstance(application)
        championDao = database.championDao()
    }

    fun getChampion(id: String): LiveData<Champion?> {
        return championDao.getChampion(id)
    }

    companion object {
        const val TAG = "ChampionDetailRepository"
    }
}