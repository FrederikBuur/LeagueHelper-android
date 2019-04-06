package com.example.leaguehelper.data.networking.staticdata

import android.content.res.Resources
import com.example.leaguehelper.R
import com.example.leaguehelper.models.staticdata.champion.ChampionsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface IStaticDataAPI {

    @GET("/cdn/{version}/data/{language}/champion.json")
    fun getChampions(
        @Path("version") version: String,
        @Path("language") language: String = Resources.getSystem().getString(R.string.static_data_language_code)
    ): Observable<ChampionsResponse>

    //get summoner spells

    //get items

    @GET("/api/versions.json")
    fun getVersions(
    ): Observable<List<String>>
}