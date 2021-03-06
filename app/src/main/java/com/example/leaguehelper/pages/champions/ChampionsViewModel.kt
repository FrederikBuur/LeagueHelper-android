package com.example.leaguehelper.pages.champions

import android.app.Application
import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.leaguehelper.R
import com.example.leaguehelper.data.LeagueHelperDatabase
import com.example.leaguehelper.models.staticdata.champion.Champion
import com.example.leaguehelper.util.genericviews.ToolBarViewModel
import me.tatarka.bindingcollectionadapter2.ItemBinding
import me.tatarka.bindingcollectionadapter2.itemBindingOf

class ChampionsViewModel(
    application: Application,
    private val onChampionClicked: (Champion, View) -> Unit
) : AndroidViewModel(application) {

    private val repo: ChampionsRepository

    val champions: LiveData<List<Champion>>
    val championItemBinding: ItemBinding<ChampionItemViewModel>
    val observableChamps: ObservableList<ChampionItemViewModel>

    init {
        val championDao = LeagueHelperDatabase.getInstance(application).championDao()
        repo = ChampionsRepository(championDao)
        champions = repo.allChampions
        championItemBinding = itemBindingOf(BR.viewModel, R.layout.item_champion)
        observableChamps = ObservableArrayList()
        initItemViewModels()
    }

    val toolbarViewModel = createChampionsToolbar()

    private fun createChampionsToolbar() : ToolBarViewModel {

        return ToolBarViewModel("Champions")
    }

    fun initItemViewModels() {
        champions.value?.let { cList ->
            cList.forEach {
                observableChamps.add(ChampionItemViewModel(it, onChampionClicked))
            }
        }
    }

}