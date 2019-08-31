package com.example.leaguehelper.pages.profile

import android.widget.EditText
import androidx.databinding.ObservableField
import com.example.leaguehelper.models.summoner.Summoner
import com.example.leaguehelper.util.ErrorHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class ProfileSearchSummonerViewModel(
    private val repository: ProfileRepository,
    private val onSummonerFound: (Summoner) -> Unit
) {

    val errorMessage = ObservableField<String>()

    fun onSearchClicked(searchEditText: EditText) {

        if (searchEditText.text.isBlank()) return

        // todo check input local before fetching remote
        CoroutineScope(Dispatchers.IO).launch {
            try {
                errorMessage.set("")
                repository.fetchSummonerByName(searchEditText.text.toString().trim()).apply {
                    updateContentWithSummoner(this)
                }
            } catch (e: Exception) {
                errorMessage.set(ErrorHandler.getErrorMessage(e))
            }
        }
    }

    private suspend fun updateContentWithSummoner(summoner: Summoner) {
        withContext(Dispatchers.Main) {
            onSummonerFound.invoke(summoner)
        }
    }

}