package com.example.leaguehelper.pages.champions

import com.example.leaguehelper.data.dao.ChampionDao

class ChampionsRepository(championDao: ChampionDao) {

    val allChampions = championDao.getAllChampions()

}