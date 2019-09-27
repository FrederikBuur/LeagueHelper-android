package com.example.leaguehelper.models.summoner

data class ChampionMastery(
    val championLevel: Int,
    val championPoints: Int,
    val championId: Long,
    val summonerId: String,
    val lastPlayTime: Long
)