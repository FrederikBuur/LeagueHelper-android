package com.example.leaguehelper.pages.main

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.leaguehelper.data.LeagueHelperDatabase
import com.example.leaguehelper.data.dao.ConfigDataDao
import com.example.leaguehelper.models.ConfigData
import com.example.leaguehelper.data.networking.staticdata.StaticDataServiceGenerator
import com.example.leaguehelper.data.networking.staticdata.IStaticDataAPI
import com.example.leaguehelper.util.LeagueToaster
import com.trello.rxlifecycle3.android.ActivityEvent
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity
import com.trello.rxlifecycle3.kotlin.bindUntilEvent
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainRepository(private val application: Application) {

    private var configDataDao: ConfigDataDao
    private var staticDataAPI: IStaticDataAPI

    private var configData: LiveData<ConfigData?>

    init {
        val database = LeagueHelperDatabase.getInstance(application)
        configDataDao = database.configDataDao()

        staticDataAPI = StaticDataServiceGenerator.createStaticDataAPI()

        configData = configDataDao.getConfigData()
    }

    fun getConfigData(): LiveData<ConfigData?> {
        return configData
    }

    @SuppressLint("CheckResult")
    fun checkForVersionUpdate(localConfigData: ConfigData?, activity: RxAppCompatActivity) {
        staticDataAPI.getVersions()
            .bindUntilEvent(activity, ActivityEvent.STOP)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ versions ->
                Log.d(TAG, "Checking for newest version")
                val latestVersion = versions.first()
                localConfigData?.let {
                    if (it.isOlderThan(latestVersion)) {
                        updateVersion(latestVersion)
                    }
                } ?: kotlin.run {
                    updateVersion(latestVersion)
                }
            }, {
                LeagueToaster.notifyOfError(application, "Error in $TAG, ${it.localizedMessage}", it)
            })
    }

    @SuppressLint("CheckResult")
    private fun updateVersion(version: String) {
        Completable.fromAction {
            val newConfigData = ConfigData(version, System.currentTimeMillis())
            configDataDao.insertOrUpdateConfigData(newConfigData)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                Log.d(TAG, "Updating local version to: $version")
            }
            .subscribe({}, {})
    }

    companion object {
        const val TAG = "MainRepository"
    }

}