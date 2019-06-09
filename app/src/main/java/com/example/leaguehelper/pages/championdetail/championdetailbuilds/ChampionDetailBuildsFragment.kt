package com.example.leaguehelper.pages.championdetail.championdetailbuilds


import android.os.Bundle
import android.view.View
import com.example.leaguehelper.pages.LeagueFragment

class ChampionDetailBuildsFragment : LeagueFragment() {

    var championIdKey: Int = 0

    private var championDetailBuildsViewModel: ChampionDetailBuildsViewModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //bind

    }


    companion object {
        fun newInstance(championIdKey: Int): ChampionDetailBuildsFragment {
            val fragment = ChampionDetailBuildsFragment()
            fragment.championIdKey = championIdKey
            return fragment
        }
    }
}
