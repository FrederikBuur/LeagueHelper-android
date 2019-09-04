package com.example.leaguehelper.pages.profile

import com.example.leaguehelper.models.leagueentry.LeagueEntry

data class ProfileInfoRanks(private val leagueEntries: List<LeagueEntry>) {

    val rankedSolo = leagueEntries.singleOrNull {
        it.queueType == LeagueEntry.Queue.RANKED_SOLO_5
    }
    val flex5 = leagueEntries.singleOrNull {
        it.queueType == LeagueEntry.Queue.RANKED_FLEX_5
    }
    val rankedTeam = leagueEntries.singleOrNull {
        it.queueType == LeagueEntry.Queue.RANKED_TEAM_5
    }
    val rankedTFT = leagueEntries.singleOrNull {
        it.queueType == LeagueEntry.Queue.RANKED_TFT
    }

}