package com.example.leaguehelper.models.leagueentry

import com.google.gson.annotations.SerializedName

data class LeagueEntry(
    val queueType: Queue,
    val summonerName: String,
    val summonerId: String,
    val miniSeries: MiniSeries,
    val wins: Int,
    val losses: Int,
    val hotStreak: Boolean,
    val tier: String,
    val rank: String,
    val leaguePoints: Int
) {

    sealed class MiniSeries(
        val progress: String,
        val wins: Int,
        val losses: Int,
        val target: Int
    )

    enum class Queue {
        @SerializedName("RANKED_SOLO_5x5")
        RANKED_SOLO_5,
        @SerializedName("RANKED_TEAM_5x5")
        RANKED_TEAM_5,
        @SerializedName("BLIND_PICK_5x5")
        BLIND_PICK_5,
        @SerializedName("RANKED_FLEX_5x5")
        RANKED_FLEX_5,
        @SerializedName("RANKED_TFT")
        RANKED_TFT,
        UNKNOWN
    }
}