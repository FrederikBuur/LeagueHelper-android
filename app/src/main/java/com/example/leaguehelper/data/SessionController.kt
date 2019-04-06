package com.example.leaguehelper.data

import com.example.leaguehelper.models.ConfigData

class SessionController {

    internal var configData: ConfigData? = null

    companion object {
        private var instance: SessionController? = null

        @Synchronized
        fun getInstance(): SessionController {
            if (instance == null) {
                instance = SessionController()
            }
            return instance!!
        }
    }

}