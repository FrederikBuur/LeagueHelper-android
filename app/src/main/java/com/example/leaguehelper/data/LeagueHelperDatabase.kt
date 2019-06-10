package com.example.leaguehelper.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.leaguehelper.data.dao.ChampionDao
import com.example.leaguehelper.data.dao.ConfigDataDao
import com.example.leaguehelper.data.dao.MatchDao
import com.example.leaguehelper.models.ConfigData
import com.example.leaguehelper.models.match.Match
import com.example.leaguehelper.models.staticdata.champion.Champion

@Database(
    entities = [Champion::class, ConfigData::class, Match::class],
    version = 1,
    exportSchema = false
)
abstract class LeagueHelperDatabase : RoomDatabase() {

    abstract fun configDataDao(): ConfigDataDao
    abstract fun championDao(): ChampionDao
    abstract fun matchDao(): MatchDao

    companion object {
        private var instance: LeagueHelperDatabase? = null

        fun getInstance(context: Context): LeagueHelperDatabase {
            return instance ?: synchronized(this) {
                val i = Room.databaseBuilder(
                    context.applicationContext,
                    LeagueHelperDatabase::class.java, "league_helper_database"
                ).fallbackToDestructiveMigration()
                    .build()
                instance = i
                i
            }
        }
    }

}