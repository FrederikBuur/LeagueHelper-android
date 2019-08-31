package com.example.leaguehelper.util.viewmodelfactory

import android.app.Application
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.leaguehelper.models.match.Match
import com.example.leaguehelper.models.summoner.Summoner
import com.example.leaguehelper.pages.profile.ProfileLoaderViewModel

class ProfileViewModelFactory(
    val application: Application,
    private val onMatchClicked: (Match, View) -> Unit
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileLoaderViewModel(application, onMatchClicked) as T
    }

}