package com.example.leaguehelper.pages.profile

import com.example.leaguehelper.models.summoner.Summoner

data class ProfileSearchSummonerViewModel(
    private val repository: ProfileRepository,
    private val onSummonerFound: (Summoner) -> Unit
) {
}