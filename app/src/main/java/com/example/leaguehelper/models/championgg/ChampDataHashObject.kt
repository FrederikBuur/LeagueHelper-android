package com.example.leaguehelper.models.championgg

import androidx.room.Embedded

data class ChampDataHashObject(
    @Embedded(prefix = "_count")
    var highestCount: ChampDataHashInfo,
    @Embedded(prefix = "_win_rate")
    var highestWinrate: ChampDataHashInfo
) {
}