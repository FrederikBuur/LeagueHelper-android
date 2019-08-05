package com.example.leaguehelper.pages.profile

import android.app.Application
import android.util.Log
import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
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

    private val MATCH_AMOUNT_TO_FETCH = 10

    private val accountId = "QV1cGw6nnNz0L4quc7uxu_jbNtv98iak52Z8zCzkjvUYOZM" // todo temp

    private val disposable = CompositeDisposable()
    private val repo: ProfileRepository

    val matches: LiveData<List<Match>>

    // data binding
    val matchItemBinding: ItemBinding<MatchHistoryItemViewModel>
    val observableMatches: ObservableList<MatchHistoryItemViewModel>
    val isFetching: ObservableBoolean

    init {
        val matchDao = LeagueHelperDatabase.getInstance(application).matchDao()
        val championDao = LeagueHelperDatabase.getInstance(application).championDao()
        repo = ProfileRepository(matchDao, championDao)
        matches = repo.matches
        matchItemBinding = itemBindingOf(BR.viewModel, R.layout.item_profile_match_history)
        observableMatches = ObservableArrayList()
        isFetching = ObservableBoolean(false)
        getMatches()
    }

    val fetchMore = {
        getMatches(observableMatches.size, observableMatches.size + MATCH_AMOUNT_TO_FETCH)
    }

    fun addMatchesToViewModel(matchesList: List<Match>) {
        val trimmedList = if (matchesList.size > MATCH_AMOUNT_TO_FETCH) {
            matchesList.subList(MATCH_AMOUNT_TO_FETCH, matchesList.size)
        } else {
            matchesList
        }
        trimmedList.forEach { match ->
            observableMatches.add(MatchHistoryItemViewModel(match, accountId, onMatchClicked, repo))
        }
    }

    private fun getMatches(beginIndex: Int = 0, endIndex: Int = MATCH_AMOUNT_TO_FETCH) {
        disposable += repo.fetchMatches(accountId, beginIndex, endIndex)
            .doOnSubscribe {
                isFetching.set(true)
            }
            .doOnComplete {
                isFetching.set(false)
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