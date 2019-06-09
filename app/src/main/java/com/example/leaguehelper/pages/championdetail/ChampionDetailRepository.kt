package com.example.leaguehelper.pages.championdetail

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.leaguehelper.data.LeagueHelperDatabase
import com.example.leaguehelper.data.dao.ChampionDao
import com.example.leaguehelper.models.staticdata.champion.Champion

class ChampionDetailRepository(
    private val championDao: ChampionDao
) {

    fun getChampion(id: String) = championDao.getChampion(id)

}