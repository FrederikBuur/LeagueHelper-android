package com.example.leaguehelper.pages.profile

import androidx.databinding.ObservableField
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

    init {
        CoroutineScope(Dispatchers.IO).launch {
            setupView()
        }
    }

    private suspend fun setupView() {
        repo.getConfigData()?.let { cd ->
            withContext(Dispatchers.Main) {
                val url = Image.getProfileIconPath(cd.version, summoner.profileIconId)
                profileIconUrl.set(url)
            }
        }
    }

}