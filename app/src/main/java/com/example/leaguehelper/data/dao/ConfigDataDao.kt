package com.example.leaguehelper.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.leaguehelper.models.ConfigData
import com.example.leaguehelper.models.staticdata.champion.Champion

@Dao
interface ConfigDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateConfigData(configData: ConfigData)

    @Query("SELECT * FROM config_data_table WHERE id = 1")
    fun getConfigData(): LiveData<ConfigData?>

    @Query("SELECT * FROM config_data_table WHERE id = 1")
    suspend fun getConfigDataSuspend(): ConfigData?
}