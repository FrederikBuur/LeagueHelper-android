package com.example.leaguehelper.pages.profile

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leaguehelper.models.leagueentry.LeagueEntry
import com.example.leaguehelper.models.staticdata.Image
import com.example.leaguehelper.models.summoner.Summoner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class ProfileInfoItemViewModel(
    val repo: ProfileRepository,
    val summoner: Summoner
) {

    val profileIconUrl = ObservableField<String>()
    val profileRanksViewModel = ObservableField<ProfileInfoRanks>()
    val profileChampionMasteryViewModel = ObservableField<ProfileChampionMasteryViewModel>()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            setupView()
        }
    }

    private suspend fun setupView() {
        fetchProfileIcon()
        fetchLeagueEntries()
        fetchChampionMasteries()
    }

    private suspend fun fetchProfileIcon() {
        repo.getConfigData()?.let { cd ->
            val url = Image.getProfileIconPath(cd.version, summoner.profileIconId)
            profileIconUrl.set(url)
        }
    }

    private suspend fun fetchLeagueEntries() {
        repo.fetchLeagueEntries(summoner.id).also { entries ->
            profileRanksViewModel.set(ProfileInfoRanks(entries))
        }

    }

    private suspend fun fetchChampionMasteries() {
        repo.fetchChampionMasteriesBySummoner(summoner.id).also { championMasteries ->
            profileChampionMasteryViewModel.set(
                ProfileChampionMasteryViewModel(
                    repo,
                    listOf(
                        championMasteries.getOrNull(0),
                        championMasteries.getOrNull(1),
                        championMasteries.getOrNull(2)
                    )
                )
            )
        }
    }
}