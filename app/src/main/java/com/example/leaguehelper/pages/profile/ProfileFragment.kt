package com.example.leaguehelper.pages.profile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.leaguehelper.R
import com.example.leaguehelper.pages.LeagueFragment
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : LeagueFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileCollapsingToolbar.setupWithNavController(profileToolBar, findNavController())
        profileToolBar.title = "Summoner Name"

    }

}
