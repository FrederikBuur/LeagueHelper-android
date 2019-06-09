package com.example.leaguehelper.pages.champions

import com.example.leaguehelper.models.staticdata.champion.Champion

interface IChampionsItemClick {
    fun onChampionClick(champion: Champion)
}