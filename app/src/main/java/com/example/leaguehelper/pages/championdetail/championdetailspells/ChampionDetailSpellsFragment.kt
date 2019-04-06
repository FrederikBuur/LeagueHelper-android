package com.example.leaguehelper.pages.championdetail.championdetailspells


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.leaguehelper.R
import com.example.leaguehelper.pages.championdetail.ChampionDetailFragmentSuper

class ChampionDetailSpellsFragment : ChampionDetailFragmentSuper() {

    private var championIdKey: Int = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_champion_detail_spells, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // setup stuff
    }

    override fun cleanUpResources() {
    }

    companion object {
        fun newInstance(championIdKey: Int): ChampionDetailSpellsFragment {
            val fragment = ChampionDetailSpellsFragment()
            fragment.championIdKey = championIdKey
            return fragment
        }
    }
}
