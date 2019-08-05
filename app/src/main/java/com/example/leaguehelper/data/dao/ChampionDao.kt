package com.example.leaguehelper.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.leaguehelper.models.staticdata.champion.Champion

@Dao
interface ChampionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateChampions(champions: List<Champion>)

    @Query("SELECT * FROM champion_table ORDER BY name ASC")
    fun getAllChampions(): LiveData<List<Champion>>

    @Query("SELECT * FROM champion_table WHERE id = :id LIMIT 1")
    fun getChampion(id: String): LiveData<Champion>

    @Query("SELECT * FROM champion_table WHERE `key` = :key LIMIT 1")
    suspend fun getChampionSuspend(key: Int): Champion?

}