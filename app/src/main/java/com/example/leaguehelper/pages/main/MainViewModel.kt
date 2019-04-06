package com.example.leaguehelper.pages.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.leaguehelper.pages.main.MainRepository
import com.example.leaguehelper.models.ConfigData
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var mainRepository = MainRepository(application)

    private var configData: LiveData<ConfigData?>

    init {
        configData = mainRepository.getConfigData()
    }

    fun getConfigData(): LiveData<ConfigData?> {
        return configData
    }

    fun checkForVersionUpdate(configData: ConfigData?, activity: RxAppCompatActivity) {
        mainRepository.checkForVersionUpdate(configData, activity)
    }



}