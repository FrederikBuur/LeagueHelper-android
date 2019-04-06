package com.example.leaguehelper.pages.championdetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.example.leaguehelper.R
import com.example.leaguehelper.models.staticdata.Image
import com.example.leaguehelper.models.staticdata.champion.Champion
import com.example.leaguehelper.pages.LeagueFragment
import com.example.leaguehelper.pages.championdetail.championdetailbuilds.ChampionDetailBuildsFragment
import com.example.leaguehelper.pages.championdetail.championdetailskins.ChampionDetailSkinsFragment
import com.example.leaguehelper.pages.championdetail.championdetailspells.ChampionDetailSpellsFragment
import com.example.leaguehelper.util.ImageLoader
import kotlinx.android.synthetic.main.fragment_champion_detail.*
import java.util.*

class ChampionDetailFragment : LeagueFragment() {

    private val args: ChampionDetailFragmentArgs by navArgs()

    private var championId: String = ""
    private var championKey: Int = 1
    private var viewPagerFragments = LinkedList<ChampionDetailFragmentSuper>()

    private var championDetailViewModel: ChampionDetailViewModel? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_champion_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        championDetailCollapsingToolbar.setupWithNavController(championDetailToolBar, findNavController())

        championId = args.championId
        championKey = args.championKey
        setupViewModel()
        setupViewPager()
    }

    private fun setupViewModel() {
        championDetailViewModel = ViewModelProviders.of(this).get(ChampionDetailViewModel::class.java)
        championDetailViewModel?.getChampions(championId)?.observe(this, Observer { champion ->
            champion?.let {
                setupCollapsingToolbar(it)
            } ?: kotlin.run {
                Log.d(TAG, "champion from view model is null")
            }
        })
    }

    private fun setupCollapsingToolbar(champion: Champion) {
        championDetailToolBar.title = champion.name
        championDetailTitle.text = champion.title
        val imgSrc = Image.getChampionSplashPath(champion.id)
        context?.let { ImageLoader.loadImage(it, imgSrc, championDetailImage) }

    }

    private fun setupViewPager() {

        viewPagerFragments.add(ChampionDetailBuildsFragment.newInstance(championKey))
        viewPagerFragments.add(ChampionDetailSpellsFragment.newInstance(championKey))
        viewPagerFragments.add(ChampionDetailSkinsFragment.newInstance(championKey))

        val pagerAdapter = ChampionDetailPagerAdapter(context, viewPagerFragments, fragmentManager)
        championDetailViewPager.adapter = pagerAdapter
        championDetailTabLayout.setupWithViewPager(championDetailViewPager)
    }

    override fun onStop() {
        super.onStop()
        viewPagerFragments.forEach { frag ->
            frag.cleanUpResources()
        }
    }

    companion object {
        const val TAG = "ChampionDetailFragment"
    }
}
