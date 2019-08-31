package com.example.leaguehelper.util

import retrofit2.HttpException

object ErrorHandler {

    fun getErrorMessage(e: Exception): String {

        return when(e) {
            is HttpException -> {
                when(e.code()) {
                    401 -> {
                        "401 - Missing API key"
                    }
                    403 -> {
                        "403 - Renew API key"
                    }
                    404 -> {
                        "404 - Does not exist"
                    }
                    429 -> {
                        "429 - Rate limit exceeded"
                    }
                    503 -> {
                        "503 - Server down"
                    }
                    else -> {
                        "${e.code()} - ${e.message()}"
                    }
                }
            }
            else -> {
                "Unsupported - ${e.localizedMessage}"
            }
        }

    }

}