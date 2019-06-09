package com.example.leaguehelper.pages.championdetail

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.leaguehelper.R
import com.example.leaguehelper.pages.LeagueFragment
import java.util.*

class ChampionDetailPagerAdapter(val context: Context?, private val fragList: LinkedList<LeagueFragment>, fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int {
        return fragList.count()
    }

    override fun getItem(position: Int): Fragment {
        return fragList[position]
    }



    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> context?.resources?.getString(R.string.champion_detail_builds_title)
            1 -> context?.resources?.getString(R.string.champion_detail_spells_title)
            else -> context?.resources?.getString(R.string.champion_detail_skins_title)
        }
    }

}