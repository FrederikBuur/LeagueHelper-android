package com.example.leaguehelper.pages.profile

import androidx.databinding.ObservableField
import com.example.leaguehelper.models.staticdata.Image
import com.example.leaguehelper.models.summoner.Summoner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class ProfileInfoItemViewModel(
    val repo: ProfileRepository,
    val summoner: Summoner
) {

//    val profileIconUrl = ObservableField<String>()
    val profileIconUrl = "http://ddragon.leagueoflegends.com/cdn/9.17.1/img/profileicon/897.png" // FIXME

    init {
        CoroutineScope(Dispatchers.IO).launch {
            setupView()
        }
    }

    private suspend fun setupView() {
        repo.getConfigData()?.let { cd ->
//            profileIconUrl.set(Image.getProfileIconPath(cd.version, summoner.profileIconId))
        }
    }

}