package com.example.leaguehelper.util

import android.content.Context
import android.widget.Toast
import com.example.leaguehelper.pages.champions.ChampionsRepository

object LeagueToaster {

    fun makeErrorToast(context: Context, msg: String, t: Throwable) {
        Toast.makeText(context, "$msg, ${t.localizedMessage}", Toast.LENGTH_LONG).show()
        t.printStackTrace()
    }

    fun makeToast(context: Context?, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}