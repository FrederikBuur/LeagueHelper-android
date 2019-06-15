package com.example.leaguehelper.util

import android.app.Application
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.leaguehelper.models.staticdata.champion.Champion
import com.example.leaguehelper.pages.champions.ChampionsViewModel

class ChampionsViewModelFactory(
    val application: Application,
    private val onChampionClicked: (Champion, View) -> Unit
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChampionsViewModel(application, onChampionClicked ) as T
    }

}