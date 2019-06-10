package com.example.leaguehelper.pages.main

import android.app.Application
import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.leaguehelper.R
import com.example.leaguehelper.data.LeagueHelperDatabase
import com.example.leaguehelper.pages.main.MainRepository
import com.example.leaguehelper.models.ConfigData
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val disposable = CompositeDisposable()
    private val repo: MainRepository

    val configData: LiveData<ConfigData>

    init {
        val configDao = LeagueHelperDatabase.getInstance(application).configDataDao()
        val championDao = LeagueHelperDatabase.getInstance(application).championDao()
        repo = MainRepository(configDao, championDao)
        configData = repo.configData
        updateIfPossible(application)
    }

    private fun updateIfPossible(application: Application) {

        val language = application.resources.getString(R.string.static_data_language_code)
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