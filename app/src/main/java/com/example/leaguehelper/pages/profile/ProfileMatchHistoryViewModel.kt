package com.example.leaguehelper.pages.profile

import android.view.View
import android.widget.Toast
import com.example.leaguehelper.models.match.Match

class ProfileMatchHistoryViewModel(
    val match: Match,
    val onMatchClicked: (Match, View) -> Unit
) {

    fun onMatchClicked(v: View) {
        onMatchClicked.invoke(match, v)
    }

    val testString = match.gameId.toString()

}