package com.example.leaguehelper.data.networking.championgg

import com.example.leaguehelper.models.championgg.ChampData
import com.example.leaguehelper.models.championgg.ChampionInformationResponse
import com.example.leaguehelper.models.staticdata.champion.ChampionsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IChampionGGAPI {

    @GET("/v2/champions/{championId}")
    fun getChampionData(
        @Path("championId") championId: Int,
        @Query("limit") limit: Int = 200,
        @Query("champData") champData: String = "hashes",
        @Query("api_key") apiKey: String = ChampionGGServiceGenerator.API_KEY
    ): Observable<List<ChampData>>
}