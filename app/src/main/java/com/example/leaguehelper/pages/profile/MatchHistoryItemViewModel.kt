package com.example.leaguehelper.pages.profile

import android.view.View
import android.widget.Toast
import com.example.leaguehelper.models.match.Match

class MatchHistoryItemViewModel(
    val match: Match,
    private val onMatchClicked: (Match, View) -> Unit
) {

    fun onMatchClicked(v: View) {
        onMatchClicked.invoke(match, v)
    }

    val testString = match.gameId.toString()

}