package com.example.leaguehelper.models.match


data class MatchesResponse(
    val beginIndex: Int,
    val endIndex: Int,
    val totalGames: Int,
    val matches: List<MatchMetaData>
)

data class MatchMetaData(
    val lane: Lane,
    val gameId: Long,
    val champion: Int,
    val platformId: String,
    val timeStamp: Long,
    val queue: Int,
    val role: String,
    val season: Int
)

