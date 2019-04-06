package com.example.leaguehelper.pages.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.leaguehelper.R
import com.example.leaguehelper.data.SessionController
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : RxAppCompatActivity() {

    var mainViewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavController()
        syncVersionIfNeeded()

    }

    private fun syncVersionIfNeeded() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel?.getConfigData()?.observe(this, Observer { configData ->
            configData?.let {
                SessionController.getInstance().configData = configData
                val currTime = System.currentTimeMillis()
                val timeThreshold = TimeUnit.HOURS.toMillis(6)
                if (currTime - timeThreshold > it.lastModified) {
                    mainViewModel?.checkForVersionUpdate(configData, this)
                } else {
                    Log.d(TAG, "Too soon to check for update")
                }
            } ?: kotlin.run {
                mainViewModel?.checkForVersionUpdate(configData, this)
            }
        })
    }

    private fun setupNavController() {
        val navController = Navigation.findNavController(this, R.id.mainNavHostFragment)
        mainBottomNavigation.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.mainNavHostFragment).navigateUp()

    companion object {
        const val TAG = "MainActivity"
    }
}
