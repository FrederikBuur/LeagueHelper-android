package com.example.leaguehelper.pages.profile

import androidx.databinding.ObservableField
import com.example.leaguehelper.models.summoner.ChampionMastery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class ProfileChampionMasteryViewModel(
    private val repo: ProfileRepository,
    private val championMasteries: List<ChampionMastery?>
) {

    val champ1 = championMasteries[0]
    val champ2 = championMasteries[1]
    val champ3 = championMasteries[2]

    val img1 = ObservableField<String>()
    val img2 = ObservableField<String>()
    val img3 = ObservableField<String>()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            champ1?.let {
                img1.set(repo.getChampionByKey(it.championId.toInt())?.championIconUrl())
            }
            champ2?.let {
                img2.set(repo.getChampionByKey(it.championId.toInt())?.championIconUrl())
            }
            champ3?.let {
                img3.set(repo.getChampionByKey(it.championId.toInt())?.championIconUrl())
            }
        }
    }



}