package com.example.leaguehelper.pages

import com.example.leaguehelper.pages.main.MainActivity
import com.trello.rxlifecycle3.components.support.RxFragment

abstract class LeagueFragment : RxFragment() {

    internal val mainActivity: MainActivity?
        get() {
            return if (activity is MainActivity) {
                activity as MainActivity
            } else {
                null
            }
        }

}