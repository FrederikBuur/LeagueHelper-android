package com.example.leaguehelper.pages.championdetail.championdetailbuilds


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import com.example.leaguehelper.R
import com.example.leaguehelper.models.championgg.ChampData
import com.example.leaguehelper.pages.LeagueFragment
import com.example.leaguehelper.pages.championdetail.ChampionDetailFragmentSuper
import com.example.leaguehelper.util.LeagueToaster
import kotlinx.android.synthetic.main.fragment_champion_detail_builds.*

class ChampionDetailBuildsFragment : ChampionDetailFragmentSuper() {

    var championIdKey: Int = 0

    private var championDetailBuildsViewModel: ChampionDetailBuildsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("", "")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_champion_detail_builds, container, false)
        Log.d("", "")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()

    }

    private fun setupViewModel() {
        championDetailBuildsViewModel = ViewModelProviders.of(this).get(ChampionDetailBuildsViewModel::class.java)
        championDetailBuildsViewModel?.getChampionBuilds(championIdKey)?.observe(this, Observer {
            if (it.isNotEmpty()) {
                setupView(it)
            }
        })

    }

    private fun setupView(champDataList: List<ChampData?>) {
        val roleList = ArrayList<String>()

        if (champDataList.isEmpty()) {
            roleList.add("None")
        } else {
            champDataList.forEach {
                roleList.add(it?.getRoleString() ?: "Default")
            }
        }

        val adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, roleList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        roleSpinner.adapter = adapter
        roleSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                champDataList[position]?.let {
                    summonerSpellsView.setupView(it)
                    startingItemsView.setupView(it, true)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }


    override fun cleanUpResources() {
        Log.d("aaaaaa", "cleanup called builds")
        if (championDetailBuildsViewModel?.getChampionBuilds(championIdKey)?.hasObservers() == true) {
            Log.d("aaaaaa", "did have observers, will now remove")
            championDetailBuildsViewModel?.getChampionBuilds(championIdKey)?.removeObservers(this)
        }
    }

    companion object {
        fun newInstance(championIdKey: Int): ChampionDetailBuildsFragment {
            val fragment = ChampionDetailBuildsFragment()
            fragment.championIdKey = championIdKey
            return fragment
        }
    }
}
