package com.example.leaguehelper.pages.champions

import android.app.Application
import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.leaguehelper.R
import com.example.leaguehelper.data.LeagueHelperDatabase
import com.example.leaguehelper.models.staticdata.champion.Champion
import me.tatarka.bindingcollectionadapter2.ItemBinding
import me.tatarka.bindingcollectionadapter2.itemBindingOf

class ChampionsViewModel(
    application: Application,
    private val onChampionClicked: (Champion, View) -> Unit
) : AndroidViewModel(application) {

    private val repo: ChampionsRepository

    val champions: LiveData<List<Champion>>
    val obsChamps: ObservableList<ChampionItemViewModel>
    val championItemView: ItemBinding<ChampionItemViewModel>

    init {
        val championDao = LeagueHelperDatabase.getInstance(application).championDao()
        repo = ChampionsRepository(championDao)
        championItemView = itemBindingOf(BR.viewModel, R.layout.item_champion)
        champions = repo.allChampions
        obsChamps = ObservableArrayList()
        initItemViewModels()
    }

    fun initItemViewModels() {

        champions.value?.let { cList ->
            obsChamps.clear()
            cList.forEach {
                obsChamps.add(ChampionItemViewModel(it, onChampionClicked))
            }
        }
    }

}