package com.example.leaguehelper.pages.champions

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.leaguehelper.R
import com.example.leaguehelper.data.LeagueHelperDatabase
import com.example.leaguehelper.models.staticdata.champion.Champion
import io.reactivex.disposables.CompositeDisposable

class ChampionsViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: ChampionsRepository

    val champions : LiveData<List<Champion>>

    init {
        val championDao = LeagueHelperDatabase.getInstance(application).championDao()
        repo = ChampionsRepository(championDao)
        champions = repo.allChampions
    }

}