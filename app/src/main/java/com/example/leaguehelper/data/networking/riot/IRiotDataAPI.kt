package com.example.leaguehelper.data.networking.riot

import com.example.leaguehelper.models.match.Match
import com.example.leaguehelper.models.match.MatchesResponse
import com.example.leaguehelper.models.summoner.Summoner
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IRiotDataAPI {

    @GET("/lol/match/v4/matchlists/by-account/{accountId}")
    fun getMatchesMetaData(
        @Path("accountId") accountId: String,
        @Query("beginIndex") beginIndex: Int,
        @Query("endIndex") endIndex: Int
    ): Observable<MatchesResponse>

    @GET("/lol/match/v4/matches/{matchId}")
    fun getMatchById(
        @Path("matchId") matchId: Long
    ): Observable<Match>

    @GET("/lol/summoner/v4/summoners/by-name/{summonerName}")
    suspend fun getSummonerByName(
        @Path("summonerName") summonerName: String
    ): Summoner

}