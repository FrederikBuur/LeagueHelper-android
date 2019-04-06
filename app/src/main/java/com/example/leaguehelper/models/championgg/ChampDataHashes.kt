package com.example.leaguehelper.models.championgg

import androidx.room.Embedded

data class ChampDataHashes(
    @Embedded(prefix = "_final_items")
    var finalitemshashfixed: ChampDataHashObject,
    @Embedded(prefix = "_skill_order")
    var skillorderhash: ChampDataHashObject,
    @Embedded(prefix = "_summoner")
    var summonershash: ChampDataHashObject,
    @Embedded(prefix = "_trinket")
    var trinkethash: ChampDataHashObject,
    @Embedded(prefix = "_first_items")
    var firstitemshash: ChampDataHashObject,
    @Embedded(prefix = "_rune")
    var runehash: ChampDataHashObject
) {
}