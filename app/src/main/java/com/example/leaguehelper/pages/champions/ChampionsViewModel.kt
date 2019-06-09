package com.example.leaguehelper.pages.champions

import android.app.Application
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.map
import com.example.leaguehelper.R
import com.example.leaguehelper.data.LeagueHelperDatabase
import com.example.leaguehelper.models.staticdata.champion.Champion
import me.tatarka.bindingcollectionadapter2.ItemBinding
import me.tatarka.bindingcollectionadapter2.OnItemBind
import me.tatarka.bindingcollectionadapter2.itemBindingOf
import me.tatarka.bindingcollectionadapter2.toItemBinding

class ChampionsViewModel(
    application: Application,
    private val onChampionClicked: (Champion) -> Unit
) : AndroidViewModel(application), IChampionsItemClick {

    private val repo: ChampionsRepository

    val champions: LiveData<List<Champion>>
    val championItemView: ItemBinding<Champion>

    init {
        val championDao = LeagueHelperDatabase.getInstance(application).championDao()
        repo = ChampionsRepository(championDao)
        champions = repo.allChampions
        championItemView = itemBindingOf<Champion>(BR.viewModel, R.layout.item_champions)
            .bindExtra(BR.listener, this)
    }

    override fun onChampionClick(champion: Champion) {
        onChampionClicked.invoke(champion)
    }

}