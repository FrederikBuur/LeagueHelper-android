package com.example.leaguehelper.pages.main

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.leaguehelper.R
import com.example.leaguehelper.models.ConfigData
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : RxAppCompatActivity() {

    private val mainViewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }

    var configData: ConfigData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.configData.observe(this, Observer { cd ->
            configData = cd
            mainViewModel.updateIfPossible()
        })
        setupNavController()
    }

    private fun setupNavController() {
        val navController = Navigation.findNavController(this, R.id.mainNavHostFragment)
        mainBottomNavigation.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.mainNavHostFragment).navigateUp()

}
