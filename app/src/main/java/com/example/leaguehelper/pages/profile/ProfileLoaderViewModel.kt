package com.example.leaguehelper.pages.profile

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.example.leaguehelper.BR
import com.example.leaguehelper.R
import com.example.leaguehelper.data.LeagueHelperDatabase
import com.example.leaguehelper.models.ConfigData
import com.example.leaguehelper.models.match.Match
import com.example.leaguehelper.models.summoner.Summoner
import com.example.leaguehelper.util.ErrorHandler
import com.example.leaguehelper.util.genericviews.ToolBarViewModel
import com.example.leaguehelper.util.genericviews.ErrorViewModel
import com.example.leaguehelper.util.genericviews.LoadingViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
                throw Exception("${this@ProfileLoaderViewModel} WTF how is this possible?")
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

    private val searchSummonerViewModel = ProfileSearchSummonerViewModel(
        onSummonerFound = { summoner ->
            CoroutineScope(Dispatchers.IO).launch {
                getConfigData()?.let { cD ->
                    item.set(loadingViewModel)
                    fetchMatches(summoner)
                    cD.copy(summoner = summoner).also {
                        repo.setConfigData(it)
                    }
                }
            }
        },
        repository = repo
    )

    private suspend fun showProfileOrFindSummoner() {
        repo.getConfigData()?.summoner?.let {
            summoner.set(it)
            fetchMatches(it)
        } ?: run {
            item.set(searchSummonerViewModel)
        }
    }

    private suspend fun getConfigData(): ConfigData? {
        return repo.getConfigData()
    }

    private fun resetSummonerConfigData() {
        CoroutineScope(Dispatchers.IO).launch {
            repo.getConfigData()?.let { cD ->
                repo.setConfigData(cD.copy(summoner = null))
            }
        }
    }

    private fun fetchMatches(sum: Summoner, beginIndex: Int = 0, endIndex: Int = MATCH_AMOUNT_TO_FETCH) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repo.fetchMatches(sum.accountId, beginIndex, endIndex)
                    .also { matches ->
                        item.set(
                            ProfileViewModel(
                                summoner = sum,
                                matches = matches,
                                disposable = disposable,
                                repo = repo,
                                onMatchClicked = onMatchClicked,
                                onResetSummonerClicked = {
                                    resetSummonerConfigData()
                                    item.set(searchSummonerViewModel)
                                }
                            )
                        )
                    }
            } catch (e: Exception) {
                ErrorHandler.getErrorMessage(this@ProfileLoaderViewModel.toString(), e)
                    .also { msg ->
                        item.set(errorViewModel.setErrorMessage(msg))
                    }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}