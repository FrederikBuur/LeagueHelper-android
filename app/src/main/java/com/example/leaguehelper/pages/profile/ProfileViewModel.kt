package com.example.leaguehelper.pages.profile

import android.util.Log
import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import com.example.leaguehelper.BR
import com.example.leaguehelper.R
import com.example.leaguehelper.models.match.Match
import com.example.leaguehelper.models.summoner.Summoner
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass

data class ProfileViewModel(
    private val summoner: Summoner,
    private val matches: List<Match>,
    private val disposable: CompositeDisposable,
    private val repo: ProfileRepository,
    private val onMatchClicked: (Match, View) -> Unit
) {
    private val MATCH_AMOUNT_TO_FETCH = 10

    val isFetching = ObservableBoolean(false)
    val items = ObservableArrayList<Any>()
    val itemView = OnItemBindClass<Any>()
        .map(ProfileInfoItemViewModel::class.java, BR.viewModel, R.layout.item_profile_info)
        .map(ProfileMatchItemViewModel::class.java, BR.viewModel, R.layout.item_profile_match_history)

    val fetchMore = {
        getMatches(items.size, items.size + MATCH_AMOUNT_TO_FETCH)
    }

    init {
        items.add(ProfileInfoItemViewModel(summoner))
        items.addAll(mapToViewModels(matches))
    }

    private fun getMatches(beginIndex: Int = 0, endIndex: Int = MATCH_AMOUNT_TO_FETCH) {
        disposable += repo.fetchMatches(summoner.accountId, beginIndex, endIndex)
            .doOnSubscribe {
                isFetching.set(true)
            }
            .doOnComplete {
                isFetching.set(false)
                Log.d(this.toString(), "matches fetched")
            }
            .subscribe({}, {
                isFetching.set(false)
                Log.d(this.toString(), it.message)
            })
    }

    private fun mapToViewModels(matchesList: List<Match>): List<ProfileMatchItemViewModel> =
//        val trimmedList = if (matchesList.size > MATCH_AMOUNT_TO_FETCH) {
//            matchesList.subList(MATCH_AMOUNT_TO_FETCH, matchesList.size)
//        } else {
//            matchesList
//        }
        matchesList.map { m ->
            m.toProfileMatchItemViewModel(summoner.accountId, onMatchClicked, repo)
        }


}