package com.example.leaguehelper.data.networking.riot

import com.example.leaguehelper.models.leagueentry.LeagueEntry
import com.example.leaguehelper.models.match.Match
import com.example.leaguehelper.models.match.MatchesResponse
import com.example.leaguehelper.models.summoner.ChampionMastery
import com.example.leaguehelper.models.summoner.Summoner
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IRiotDataAPI {

    @GET("/lol/match/v4/matchlists/by-account/{accountId}")
    suspend fun getMatchesMetaData(
        @Path("accountId") accountId: String,
        @Query("beginIndex") beginIndex: Int,
        @Query("endIndex") endIndex: Int
    ): MatchesResponse

    @GET("/lol/match/v4/matches/{matchId}")
    suspend fun getMatchById(
        @Path("matchId") matchId: Long
    ): Match

    @GET("/lol/league/v4/entries/by-summoner/{summonerId}")
    suspend fun getLeagueEntries(
        @Path("summonerId") summonerId: String
    ): List<LeagueEntry>

    @GET("/lol/summoner/v4/summoners/by-name/{summonerName}")
    suspend fun getSummonerByName(
        @Path("summonerName") summonerName: String
    ): Summoner

    @GET("/lol/champion-mastery/v4/champion-masteries/by-summoner/{summonerId}")
    suspend fun getChampionMastieriesBySummoner(
        @Path("summonerId") summonerId: String
    ): List<ChampionMastery>

}