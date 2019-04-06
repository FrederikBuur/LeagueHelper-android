package com.example.leaguehelper.pages.championdetail.championdetailskins


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.leaguehelper.R
import com.example.leaguehelper.pages.championdetail.ChampionDetailFragmentSuper
import com.example.leaguehelper.pages.championdetail.championdetailbuilds.ChampionDetailBuildsFragment

class ChampionDetailSkinsFragment : ChampionDetailFragmentSuper() {

    private var championIdKey: Int = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_champion_detail_skins, container, false)
    }

    override fun cleanUpResources() {
    }

    companion object {
        fun newInstance(championIdKey: Int): ChampionDetailSkinsFragment {
            val fragment = ChampionDetailSkinsFragment()
            fragment.championIdKey = championIdKey
            return fragment
        }
    }
}
