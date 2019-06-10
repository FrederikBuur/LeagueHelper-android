package com.example.leaguehelper.models.match

import com.google.gson.annotations.SerializedName

enum class Lane { // TODO not used
    @SerializedName("TOP")
    TOP,
    @SerializedName("JUNGLE")
    JUNGLE,
    @SerializedName("MID")
    MID,
    @SerializedName("BOTTOM")
    BOTTOM,
    @SerializedName("SUPPORT")
    SUPPORT,
    @SerializedName("NONE")
    NONE
}

enum class QueueType {
    @SerializedName("420")
    RANKED_SOLO_5,
    @SerializedName("430")
    BLIND_PICK_5,
    @SerializedName("440")
    RANKED_FLEX_5,
    @SerializedName("450")
    ARAM_5,
    @SerializedName("460")
    BLIND_PICK_3,
    @SerializedName("470")
    RANKED_FLEX_3,
    DEFAULT
}

enum class TeamColor {
    @SerializedName("100")
    BLUE,
    @SerializedName("200")
    RED,
    DEFAULT
}

enum class TeamResult {
    @SerializedName("Win")
    WIN,
    @SerializedName("Fail")
    LOSS,
    DEFAULT
}