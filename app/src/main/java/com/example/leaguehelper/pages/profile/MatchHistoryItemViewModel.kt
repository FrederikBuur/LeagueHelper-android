package com.example.leaguehelper.pages.profile

import android.view.View
import com.example.leaguehelper.models.match.Match
import java.text.SimpleDateFormat
import java.util.*

class MatchHistoryItemViewModel(
    val match: Match,
    private val onMatchClicked: (Match, View) -> Unit
) {

    fun onMatchClicked(v: View) {
        onMatchClicked.invoke(match, v)
    }

    fun isWin(): Boolean {
        return true
    }

    fun timestamp(): String {
        var timestampText = ""
        val matchDate = Date(match.gameCreation)
        val currDate = Date()
        val fmt = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        // if today show clock instead of date
        val pattern = if (fmt.format(matchDate) == fmt.format(currDate)) {
            timestampText = "Today "
            "HH:mm"
        } else {
            "dd MMMM YYYY"
        }
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        return "$timestampText${formatter.format(matchDate)}"
    }

    fun gameLength(): String {
        val min = match.gameDuration / 60
        val sec = match.gameDuration % 60
        return "$min:$sec"
    }

    init {

    }

}