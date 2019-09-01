package com.example.leaguehelper.models.staticdata

data class Image(
    var full: String
) {

    companion object {
        fun getSummonerSpellIconPath(version: String, spellName: String): String {
            return "http://ddragon.leagueoflegends.com/cdn/$version/img/spell/SummonerFlash.png"
        }

        fun getItemIconPath(version: String, itemNumber: String): String {
            return "http://ddragon.leagueoflegends.com/cdn/$version/img/item/$itemNumber.png"
        }

        fun getProfileIconPath(version: String, iconId: Int): String {
            return "http://ddragon.leagueoflegends.com/cdn/$version/img/profileicon/$iconId.png "
        }

    }

}
