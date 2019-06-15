package com.example.leaguehelper.pages.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.leaguehelper.R
import com.example.leaguehelper.data.LeagueHelperDatabase
import com.example.leaguehelper.models.ConfigData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

class MainViewModel(private val app: Application) : AndroidViewModel(app) {

    private val disposable = CompositeDisposable()
    private val repo: MainRepository

    val configData: LiveData<ConfigData?>

    init {
        val configDao = LeagueHelperDatabase.getInstance(app).configDataDao()
        val championDao = LeagueHelperDatabase.getInstance(app).championDao()
        repo = MainRepository(configDao, championDao)
        configData = repo.configData
    }

    fun updateIfPossible() {

        val language = app.resources.getString(R.string.static_data_language_code)
        disposable += repo.checkForUpdates(language)
            .switchIfEmpty {
                // not updated
                Log.d(this.toString(), "yay no need to update")
            }
            .subscribe({
                // updated
                Log.d(this.toString(), "yay update finished")
            }, {
                it.printStackTrace()
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}