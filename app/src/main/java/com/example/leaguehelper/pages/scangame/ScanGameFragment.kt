package com.example.leaguehelper.pages.scangame


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.leaguehelper.R
import com.example.leaguehelper.databinding.FragmentScanGameBinding
import com.example.leaguehelper.util.LeagueFragment
import com.example.leaguehelper.util.genericviews.ToolBarViewModel
import kotlinx.android.synthetic.main.fragment_scan_game.*

class ScanGameFragment : LeagueFragment() {

    val toolbar =
        ToolBarViewModel("Scan game") // todo temp should be in scan game loader view model

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentScanGameBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_scan_game, container, false)
        binding.viewModel = this
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (scanGameToolbar as? Toolbar)?.setupWithNavController(findNavController())

    }

}
