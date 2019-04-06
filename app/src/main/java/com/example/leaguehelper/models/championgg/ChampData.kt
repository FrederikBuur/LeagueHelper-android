package com.example.leaguehelper.models.championgg

import androidx.room.Embedded
import androidx.room.Entity

@Entity(
    tableName = "champion_gg_champ_data_table",
    primaryKeys = ["championId", "role"]
)
data class ChampData(
    var elo: String,
    var patch: String,
    var championId: Int,
    var winRate: Float,
    var playRate: Float,
    var role: String,
    @Embedded(prefix = "_hashes")
    var hashes: ChampDataHashes
) {

    fun getRoleString(): String {
        return when (role) {
            "TOP" -> "Top"
            "JUNGLE" -> "Jungle"
            "MIDDLE" -> "Mid"
            "DUO_CARRY" -> "Bottom"
            "DUO_SUPPORT" -> "Support"
            else -> "Error"
        }
    }
}