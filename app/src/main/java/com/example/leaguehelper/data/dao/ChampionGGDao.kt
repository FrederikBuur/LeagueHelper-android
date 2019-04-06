package com.example.leaguehelper.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.leaguehelper.models.championgg.ChampData

@Dao
interface ChampionGGDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateChampData(champions: List<ChampData>)

    @Query("SELECT * FROM champion_gg_champ_data_table WHERE championId = :id")
    fun getChampData(id: Int): LiveData<List<ChampData?>>

    @Query("SELECT * FROM champion_gg_champ_data_table WHERE championId = :id")
    fun getChampDataSync(id: Int): List<ChampData?>
}