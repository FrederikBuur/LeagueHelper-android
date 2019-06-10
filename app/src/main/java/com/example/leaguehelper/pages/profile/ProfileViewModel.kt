package com.example.leaguehelper.pages.profile

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.leaguehelper.data.LeagueHelperDatabase
import com.example.leaguehelper.models.match.Match
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

class ProfileViewModel(
    application: Application
    //private val accountId: String // TODO create custom factory
) : AndroidViewModel(application) {

    private val accountId = "QBP6sESg5Y_gIhjQfjf-yK-LJbMHjV-b1oM9mzL67-0" // todo temp

    private val disposable = CompositeDisposable()
    private val repo: ProfileRepository

    val matches: LiveData<List<Match>>

    init {
        val matchDao = LeagueHelperDatabase.getInstance(application).matchDao()
        repo = ProfileRepository(matchDao)
        matches = repo.matches
        getMatches()
    }

    fun getMatches(beginIndex: Int = 0, endIndex: Int = 10) {
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