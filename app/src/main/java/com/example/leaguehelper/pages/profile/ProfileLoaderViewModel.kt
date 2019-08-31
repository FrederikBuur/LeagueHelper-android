package com.example.leaguehelper.pages.profile

import android.app.Application
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.example.leaguehelper.BR
import com.example.leaguehelper.R
import com.example.leaguehelper.data.LeagueHelperDatabase
import com.example.leaguehelper.models.match.Match
import com.example.leaguehelper.models.summoner.Summoner
import com.example.leaguehelper.util.genericviews.ToolBarViewModel
import com.example.leaguehelper.util.genericviews.ErrorViewModel
import com.example.leaguehelper.util.genericviews.LoadingViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass
import java.lang.Exception

class ProfileLoaderViewModel(
    application: Application,
    private val onMatchClicked: (Match, View) -> Unit
) : AndroidViewModel(application) {

    private val MATCH_AMOUNT_TO_FETCH = 10

//    private val accountId = "QV1cGw6nnNz0L4quc7uxu_jbNtv98iak52Z8zCzkjvUYOZM" // todo temp

    private val disposable = CompositeDisposable()
    private val repo: ProfileRepository

    private val summoner = ObservableField<Summoner>()

    private val loadingViewModel = LoadingViewModel()

    private val errorViewModel = ErrorViewModel(
        message = application.applicationContext.getString(R.string.error_view_message),
        icon = R.drawable.ic_error,
        onRetryClicked = {
            item.set(loadingViewModel)
            summoner.get()?.let {
                fetchMatches(it)
            } ?: run {
                throw Exception()
            }
        }
    )

    val toolbar = ToolBarViewModel("Profile")

    val item = ObservableField<Any>(loadingViewModel)

    val itemBinding: OnItemBindClass<Any> = OnItemBindClass<Any>()
        .map(LoadingViewModel::class.java, BR.viewModel, R.layout.view_generic_loading)
        .map(ErrorViewModel::class.java, BR.viewModel, R.layout.view_generic_error)
        .map(ProfileViewModel::class.java, BR.viewModel, R.layout.view_profile)
        .map(ProfileSearchSummonerViewModel::class.java, BR.viewModel, R.layout.view_profile_search_summoner)


    init {
        val matchDao = LeagueHelperDatabase.getInstance(application).matchDao()
        val championDao = LeagueHelperDatabase.getInstance(application).championDao()
        val configData = LeagueHelperDatabase.getInstance(application).configDataDao()
        repo = ProfileRepository(matchDao, championDao, configData)

        CoroutineScope(Dispatchers.IO).launch {
            showProfileOrFindSummoner()
        }
    }

    private suspend fun showProfileOrFindSummoner() {
        repo.getSummoner()?.let {
            fetchMatches(it)
        } ?: run {
            withContext(Dispatchers.Main) {
                item.set(
                    ProfileSearchSummonerViewModel(
                        onSummonerFound = { summoner ->
                            item.set(loadingViewModel)
                            fetchMatches(summoner)
                        },
                        repository = repo
                    )
                )
            }
        }
    }

    private fun fetchMatches(sum: Summoner, beginIndex: Int = 0, endIndex: Int = MATCH_AMOUNT_TO_FETCH) {
        disposable += repo.fetchMatches(sum.accountId, beginIndex, endIndex)
            .subscribe({
                item.set(
                    ProfileViewModel(
                        summoner = sum,
                        matches = repo.matches.value ?: emptyList(),
                        disposable = disposable,
                        onMatchClicked = onMatchClicked,
                        repo = repo
                    )
                )
            }, {
                item.set(errorViewModel)
                Log.d(this.toString(), it.message)
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}