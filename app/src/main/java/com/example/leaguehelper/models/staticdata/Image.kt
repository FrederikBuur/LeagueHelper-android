package com.example.leaguehelper.models.staticdata

data class Image(
    var full: String
) {

    companion object {
        fun getChampionIconPath(version: String, imgName: String): String {
            return "http://ddragon.leagueoflegends.com/cdn/$version/img/champion/$imgName"
        }

        fun getChampionSplashPath(championName: String): String {
            return "http://ddragon.leagueoflegends.com/cdn/img/champion/splash/${championName}_0.jpg"
        }

        fun getSummonerSpellIconPath(version: String, spellName: String): String {
            return "http://ddragon.leagueoflegends.com/cdn/6.24.1/img/spell/SummonerFlash.png"
        }

        fun getItemIconPath(version: String, itemNumber: String): String {
            return "http://ddragon.leagueoflegends.com/cdn/$version/img/item/$itemNumber.png"
        }
    }

}
