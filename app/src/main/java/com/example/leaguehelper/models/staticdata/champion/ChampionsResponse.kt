package com.example.leaguehelper.models.staticdata.champion

data class ChampionsResponse(
    var type: String,
    var version: String,
    var data: Map<String, Champion>
) {
}
