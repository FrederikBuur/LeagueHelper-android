package com.example.leaguehelper.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.leaguehelper.models.match.Match

@Dao
interface MatchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateMatches(matches: List<Match>)

    @Query("SELECT * FROM match_table ORDER BY gameCreation ASC")
    fun getAllMatches(): LiveData<List<Match>>

}