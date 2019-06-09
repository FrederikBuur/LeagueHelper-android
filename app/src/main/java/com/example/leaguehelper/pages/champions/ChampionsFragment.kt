package com.example.leaguehelper.pages.champions


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.leaguehelper.R
import com.example.leaguehelper.databinding.FragmentChampionsBinding
import com.example.leaguehelper.models.staticdata.champion.Champion
import com.example.leaguehelper.pages.LeagueFragment
import com.example.leaguehelper.util.ChampionsViewModelFactory
import kotlinx.android.synthetic.main.fragment_champions.*

class ChampionsFragment : LeagueFragment() {

    private val championsViewModel by lazy {
        activity?.let { act ->
            ViewModelProviders.of(this,
                ChampionsViewModelFactory(act.application) { champion -> navigateToDetail(champion) }
            ).get(ChampionsViewModel::class.java)
        } ?: run {
            ViewModelProviders.of(this).get(ChampionsViewModel::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentChampionsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_champions, container, false)
        binding.viewModel = championsViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun navigateToDetail(champion: Champion) {
        val id = champion.id
        val key = champion.key
        val action = ChampionsFragmentDirections.toChampionDetail(id, key)
        findNavController().navigate(action)
    }

    private fun setupView() {
        //setup toolbar
        championsToolbar.setupWithNavController(findNavController()) // TODO add search in toolbar
        championsToolbar.title = "Search Champion"
    }

}
