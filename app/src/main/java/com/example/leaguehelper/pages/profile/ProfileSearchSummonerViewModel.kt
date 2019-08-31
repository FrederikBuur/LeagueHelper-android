package com.example.leaguehelper.pages.profile

import android.widget.EditText
import androidx.databinding.ObservableField
import com.example.leaguehelper.models.summoner.Summoner
import com.example.leaguehelper.util.ErrorHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
                repository.fetchSummonerByName(searchEditText.text.toString().trim())
                    .also { summoner ->
                        onSummonerFound.invoke(summoner)
                    }
            } catch (e: Exception) {
                ErrorHandler.getErrorMessage(this@ProfileSearchSummonerViewModel.toString(), e)
                    .also { msg ->
                        errorMessage.set(msg)
                    }
            }
        }
    }
}