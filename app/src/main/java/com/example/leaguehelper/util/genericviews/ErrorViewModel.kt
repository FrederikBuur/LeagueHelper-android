package com.example.leaguehelper.util.genericviews

import com.example.leaguehelper.R

data class ErrorViewModel(val message: String,
                          val icon: Int = R.drawable.ic_error,
                          val onRetryClicked: () -> Unit)