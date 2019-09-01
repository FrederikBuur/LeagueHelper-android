package com.example.leaguehelper.pages.profile

import android.view.View
import androidx.databinding.ObservableField
import com.example.leaguehelper.models.match.Match
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

data class ProfileMatchItemViewModel(
    private val match: Match,
    private val accountId: String,
    private val onMatchClicked: (Match, View) -> Unit,
    private val repo: ProfileRepository
) {

    val championUrl = ObservableField<String>()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            setupView()
        }
    }

    private suspend fun setupView() {

        val participant = match.participantIdentities.singleOrNull { pId ->
            pId.player.accountId == accountId
        }?.let { innerPId ->
            match.participants.singleOrNull { p ->
                p.participantId == innerPId.participantId
            }
        }

        participant?.let { p ->
            repo.getChampionByKey(p.championId)?.let { champ ->
                withContext(Dispatchers.Main) {
                    championUrl.set(champ.championIconUrl())
                }
            }
        }
    }

    fun onMatchClicked(v: View) {
        onMatchClicked.invoke(match, v)
    }

    fun isWin(): Boolean {
        return true
    }

    fun timestamp(): String {
        var timestampText = ""
        val matchDate = Date(match.gameCreation)
        val currDate = Date()
        val fmt = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        // if today show clock instead of date
        val pattern = if (fmt.format(matchDate) == fmt.format(currDate)) {
            timestampText = "Today "
            "HH:mm"
        } else {
            "dd MMMM YYYY"
        }
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        return "$timestampText${formatter.format(matchDate)}"
    }

    fun gameLength(): String {
        val min = match.gameDuration / 60
        val sec = match.gameDuration % 60
        return "$min:$sec"
    }

}

fun Match.toProfileMatchItemViewModel(
    accountId: String, onMatchClicked: (Match, View) -> Unit, repo: ProfileRepository
): ProfileMatchItemViewModel {
    return ProfileMatchItemViewModel(this, accountId, onMatchClicked, repo)
}