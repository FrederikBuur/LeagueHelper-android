package com.example.leaguehelper.pages.profile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.leaguehelper.R
import com.example.leaguehelper.databinding.FragmentProfileBinding
import com.example.leaguehelper.models.match.Match
import com.example.leaguehelper.pages.LeagueFragment
import com.example.leaguehelper.util.viewmodelfactory.ProfileViewModelFactory
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : LeagueFragment() {

    private val profileViewModel by lazy {
        activity?.let { act ->
            ViewModelProviders.of(this,
                ProfileViewModelFactory(act.application) { match, view ->
                    navigateToMatchDetail(
                        match,
                        view
                    )
                }
            ).get(ProfileViewModel::class.java)
        } ?: run {
            ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.viewModel = profileViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileCollapsingToolbar.setupWithNavController(profileToolBar, findNavController())
        profileToolBar.title = "Summoner Name"

        profileViewModel.matches.observe(this, Observer { matches ->
           profileViewModel.addMatchesToViewModel(matches)
        })
    }

    private fun navigateToMatchDetail(match: Match, view: View) {
        Toast.makeText(context, "TODO: navigate to match", Toast.LENGTH_SHORT).show()
//        val id = champion.id
//        val key = champion.key
//        val action = ChampionsFragmentDirections.toChampionDetail(id, key)
//        view.findNavController().navigate(action)
    }

}
