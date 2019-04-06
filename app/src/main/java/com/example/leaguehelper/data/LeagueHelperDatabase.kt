package com.example.leaguehelper.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.leaguehelper.data.dao.ChampionDao
import com.example.leaguehelper.data.dao.ChampionGGDao
import com.example.leaguehelper.data.dao.ConfigDataDao
import com.example.leaguehelper.models.ConfigData
import com.example.leaguehelper.models.championgg.ChampData
import com.example.leaguehelper.models.staticdata.champion.Champion

@Database(
    entities = [Champion::class, ConfigData::class, ChampData::class],
    version = 1,
    exportSchema = false
)
abstract class LeagueHelperDatabase : RoomDatabase() {

    abstract fun configDataDao(): ConfigDataDao
    abstract fun championDao(): ChampionDao
    abstract fun championGGDao(): ChampionGGDao

    companion object {

        private var instance: LeagueHelperDatabase? = null

        @Synchronized
        fun getInstance(context: Context): LeagueHelperDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    LeagueHelperDatabase::class.java, "league_helper_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }

    }

}