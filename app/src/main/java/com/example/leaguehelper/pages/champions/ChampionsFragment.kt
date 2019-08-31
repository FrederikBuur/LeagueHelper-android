package com.example.leaguehelper.pages.champions


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.leaguehelper.R
import com.example.leaguehelper.databinding.FragmentChampionsBinding
import com.example.leaguehelper.models.staticdata.champion.Champion
import com.example.leaguehelper.util.LeagueFragment
import com.example.leaguehelper.util.viewmodelfactory.ChampionsViewModelFactory
import kotlinx.android.synthetic.main.fragment_champions.*

class ChampionsFragment : LeagueFragment() {

    private val championsViewModel by lazy {
        activity?.let { act ->
            ViewModelProviders.of(this,
                ChampionsViewModelFactory(act.application) { champion, view ->
                    navigateToDetail(
                        champion,
                        view
                    )
                }
            ).get(ChampionsViewModel::class.java)
        } ?: run {
            ViewModelProviders.of(this).get(ChampionsViewModel::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentChampionsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_champions, container, false)
        binding.viewModel = championsViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupView() {
        //setup toolbar
        (championsToolbar as? Toolbar)?.setupWithNavController(findNavController()) // TODO add search in toolbar

        // setup champions observer
        championsViewModel.champions.observe(this, Observer {
            championsViewModel.initItemViewModels()
        })
    }

    private fun navigateToDetail(champion: Champion, view: View) {
        val id = champion.id
        val key = champion.key
        val action = ChampionsFragmentDirections.toChampionDetail(id, key)
        view.findNavController().navigate(action)
    }
}
