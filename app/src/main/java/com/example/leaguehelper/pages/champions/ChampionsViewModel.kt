package com.example.leaguehelper.pages.champions

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.leaguehelper.pages.champions.ChampionsRepository
import com.example.leaguehelper.models.staticdata.champion.Champion
import com.trello.rxlifecycle3.components.support.RxFragment

class ChampionsViewModel(application: Application) : AndroidViewModel(application) {

    private var championsRepository = ChampionsRepository(application)

    private var champions: LiveData<List<Champion>?>

    init {
        champions = championsRepository.getChampions()
    }

    fun getChampions(): LiveData<List<Champion>?> {
        return champions
    }

    fun fetchChampions(fragment: RxFragment) {
        championsRepository.fetchChampions(fragment)
    }

}