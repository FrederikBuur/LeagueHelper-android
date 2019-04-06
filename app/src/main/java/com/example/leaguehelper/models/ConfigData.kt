package com.example.leaguehelper.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "config_data_table")
data class ConfigData(
    var version: String,
    var lastModified: Long
) {

    @PrimaryKey
    var id: Int = 1

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