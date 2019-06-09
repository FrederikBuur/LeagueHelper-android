package com.example.leaguehelper.models.staticdata.champion

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.leaguehelper.models.staticdata.Image

@Entity(tableName = "champion_table")
data class Champion(
    @PrimaryKey
    var id: String,
    var version: String,
    var key: Int,
    var name: String,
    var title: String,
    var blurb: String,
    @Embedded(prefix = "image_")
    var image: Image
) {

    fun championIconUrl(): String {
        return "https://ddragon.leagueoflegends.com/cdn/$version/img/champion/${image.full}"
    }

    fun championSplashUrl(): String {
        return "http://ddragon.leagueoflegends.com/cdn/img/champion/splash/${name}_0.jpg"
    }

}
