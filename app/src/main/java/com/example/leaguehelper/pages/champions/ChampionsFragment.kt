package com.example.leaguehelper.pages.champions


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.leaguehelper.R
import com.example.leaguehelper.data.SessionController
import com.example.leaguehelper.models.staticdata.champion.Champion
import com.example.leaguehelper.pages.LeagueFragment
import com.turingtechnologies.materialscrollbar.AlphabetIndicator
import kotlinx.android.synthetic.main.fragment_champions.*

class ChampionsFragment : LeagueFragment() {

    private var championsViewModel: ChampionsViewModel? = null
    private var adapter: ChampionsAdapter? = null

    private var championList: ArrayList<Champion> = ArrayList()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_champions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        championsToolbar.setupWithNavController(findNavController())
        championsToolbar.title = "Search Champion"

        setupRecyclerView()
        setupViewModel()
    }

    private fun setupViewModel() {
        championsViewModel = ViewModelProviders.of(this).get(ChampionsViewModel::class.java)
        championsViewModel?.getChampions()?.observe(this, Observer { champions ->
            champions?.let {
                val configData = SessionController.getInstance().configData
                val champVersion = it.firstOrNull()?.version ?: "0.0.0"
                if (it.isEmpty() || (configData?.isOlderThan(champVersion) != true && configData?.version != champVersion)) {
                    championsViewModel?.fetchChampions(this)
                } else {
                    // update adapter if new content
                    championList.clear()
                    championList.addAll(it)
                    adapter?.submitList(it)
                }
            } ?: kotlin.run {
                // fetch champions into room
                if (champions != null) return@run
                championsViewModel?.fetchChampions(this)
            }
        })
    }

    private fun setupRecyclerView() {
        context?.let {
            if (adapter == null) {
                adapter = ChampionsAdapter(it)
            }
            championsRecyclerView.adapter = adapter
            dragScrollBar.setIndicator(AlphabetIndicator(it), true)
        }
    }

    companion object {
        const val TAG = "ChampionsFragment"
    }
}
