package com.example.leaguehelper.util.generics

import com.example.leaguehelper.R

data class ErrorViewModel(val message: String,
                          val icon: Int = R.drawable.ic_error,
                          val onRetryClicked: () -> Unit)