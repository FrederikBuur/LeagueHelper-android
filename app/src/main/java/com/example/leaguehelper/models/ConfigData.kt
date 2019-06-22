package com.example.leaguehelper.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.leaguehelper.models.summoner.Summoner

@Entity(tableName = "config_data_table")
data class ConfigData(
    val version: String,
    val lastModified: Long,
    @Embedded(prefix = "summoner_")
    val summoner: Summoner?
) {

    @PrimaryKey
    var id: Int = 1 // to ensure only one config will be stored

    fun isOlderThan(newVersion: String): Boolean {

        val current = version.split(".")
        val new = newVersion.split(".")

        current.forEachIndexed { index, curr ->
            val currInt = curr.toIntOrNull()
            val newInt = new[index].toIntOrNull()

            if ((currInt ?: 0) < (newInt ?: 0)) {
                return true
            }
        }
        return false

    }

}