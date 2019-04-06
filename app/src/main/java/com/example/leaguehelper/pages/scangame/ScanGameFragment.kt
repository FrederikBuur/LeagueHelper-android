package com.example.leaguehelper.pages.scangame


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.leaguehelper.R
import com.example.leaguehelper.pages.LeagueFragment
import kotlinx.android.synthetic.main.fragment_scan_game.*

class ScanGameFragment : LeagueFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_scan_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scanGameToolbar.setupWithNavController(findNavController())
        scanGameToolbar.title = "Scan Game"

    }

}
