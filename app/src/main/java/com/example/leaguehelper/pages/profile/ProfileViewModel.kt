package com.example.leaguehelper.pages.profile

import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import com.example.leaguehelper.BR
import com.example.leaguehelper.R
import com.example.leaguehelper.models.match.Match
import com.example.leaguehelper.models.summoner.Summoner
import com.example.leaguehelper.util.ErrorHandler
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.tatarka.bindingcollectionadapter2.itembindings.OnItemBindClass
import java.lang.Exception

data class ProfileViewModel(
    private val summoner: Summoner,
    private val matches: List<Match>,
    private val disposable: CompositeDisposable,
    private val repo: ProfileRepository,
    private val onMatchClicked: (Match, View) -> Unit,
    private val onResetSummonerClicked: () -> Unit
) {
    private val MATCH_AMOUNT_TO_FETCH = 10

    val isFetching = ObservableBoolean(false)
    val items = ObservableArrayList<Any>()
    val itemView: OnItemBindClass<Any> = OnItemBindClass<Any>()
        .map(ProfileInfoItemViewModel::class.java, BR.viewModel, R.layout.item_profile_info)
        .map(ProfileMatchItemViewModel::class.java, BR.viewModel, R.layout.item_profile_match_history)

    val fetchMore = {
        getMatches(items.size, items.size + MATCH_AMOUNT_TO_FETCH)
    }

    fun onResetClicked(v: View) {
        onResetSummonerClicked()
    }

    init {
        items.add(ProfileInfoItemViewModel(repo, summoner))
        items.addAll(mapToViewModels(matches))
    }

    private fun getMatches(beginIndex: Int = 0, endIndex: Int = MATCH_AMOUNT_TO_FETCH) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                isFetching.set(true)
                repo.fetchMatches(summoner.accountId, beginIndex, endIndex)
                    .also { matches ->
                        withContext(Dispatchers.Main) {
                            items.addAll(mapToViewModels(matches))
                        }
                        isFetching.set(false)
                    }
            } catch (e: Exception) {
                isFetching.set(false)
                ErrorHandler.getErrorMessage(this@ProfileViewModel.toString(), e)
            }
        }
    }

    private fun mapToViewModels(matchesList: List<Match>): List<ProfileMatchItemViewModel> =
        matchesList.map { m ->
            m.toProfileMatchItemViewModel(summoner.accountId, onMatchClicked, repo)
        }
}