package com.example.leaguehelper.pages.profile

import android.app.Application
import android.util.Log
import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.leaguehelper.BR
import com.example.leaguehelper.R
import com.example.leaguehelper.data.LeagueHelperDatabase
import com.example.leaguehelper.models.match.Match
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import me.tatarka.bindingcollectionadapter2.ItemBinding
import me.tatarka.bindingcollectionadapter2.itemBindingOf

class ProfileViewModel(
    application: Application,
    private val onMatchClicked: (Match, View) -> Unit
    //private val accountId: String // TODO create custom factory
) : AndroidViewModel(application) {

    private val accountId = "QBP6sESg5Y_gIhjQfjf-yK-LJbMHjV-b1oM9mzL67-0" // todo temp

    private val disposable = CompositeDisposable()
    private val repo: ProfileRepository

    val matches: LiveData<List<Match>>

    // data binding
    val matchItemBinding: ItemBinding<MatchHistoryItemViewModel>
    val observableMatches: ObservableList<MatchHistoryItemViewModel>

    init {
        val matchDao = LeagueHelperDatabase.getInstance(application).matchDao()
        repo = ProfileRepository(matchDao)
        matches = repo.matches
        matchItemBinding = itemBindingOf(BR.viewModel, R.layout.item_profile_match_history)
        observableMatches = ObservableArrayList()
        getMatches()
    }

    val fetchMore = { getMatches(observableMatches.size, observableMatches.size + 20) }

    fun addMatchesToViewModel(matchesList: List<Match>) {
        matchesList.forEach {
            observableMatches.add(MatchHistoryItemViewModel(it, onMatchClicked))
        }
    }

    private fun getMatches(beginIndex: Int = 0, endIndex: Int = 10) {
        disposable += repo.fetchMatches(accountId, beginIndex, endIndex)
            .doOnComplete {
                Log.d(this.toString(), "matches fetched")
            }
            .subscribe({}, {
                Log.d(this.toString(), it.message)
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}