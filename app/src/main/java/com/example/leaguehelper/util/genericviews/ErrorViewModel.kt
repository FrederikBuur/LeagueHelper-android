package com.example.leaguehelper.util.genericviews

import androidx.databinding.ObservableField
import com.example.leaguehelper.R

data class ErrorViewModel(
    private val message: String,
    val icon: Int = R.drawable.ic_error,
    val onRetryClicked: () -> Unit
) {

    val errorMessage = ObservableField(message)

    fun setErrorMessage(msg: String): ErrorViewModel {
        errorMessage.set(msg)
        return this
    }

}