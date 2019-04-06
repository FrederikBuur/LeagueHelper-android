package com.example.leaguehelper.pages.championdetail.championdetailbuilds

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.leaguehelper.data.LeagueHelperDatabase
import com.example.leaguehelper.data.SessionController
import com.example.leaguehelper.data.dao.ChampionGGDao
import com.example.leaguehelper.data.networking.championgg.ChampionGGServiceGenerator
import com.example.leaguehelper.models.championgg.ChampData
import com.example.leaguehelper.util.LeagueToaster
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ChampionDetailBuildsRepository(val application: Application) {

    private var championGGDao: ChampionGGDao

    init {
        val database = LeagueHelperDatabase.getInstance(application)
        championGGDao = database.championGGDao()
    }

    fun getChampData(id: Int): LiveData<List<ChampData?>> {
        val dataLive = championGGDao.getChampData(id)
        val version = SessionController.getInstance().configData?.version

        fetchChampDataIfNeeded(id, version)

        return dataLive
    }

    @SuppressLint("CheckResult")
    private fun fetchChampDataIfNeeded(id: Int, version: String?) {
        Completable.fromAction {
            val dataSync = championGGDao.getChampDataSync(id)
            if (dataSync.isEmpty()) {
                return@fromAction fetchChampData(id)
            }
            dataSync.forEach { champData ->
                champData?.let {
                    if (version?.startsWith(champData.patch) != true) {
                        // champ data in room is outdated. Get from server
                        fetchChampData(id)
                    }
                } ?: kotlin.run {
                    // no champ data in room get from server
                    fetchChampData(id)
                }
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {
                it
            })
    }

    @SuppressLint("CheckResult")
    private fun fetchChampData(championId: Int){
        ChampionGGServiceGenerator.createChampionGGAPI().getChampionData(championId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        //.bind()
            .subscribe({ champDataList ->
                updateChampData(champDataList)
            }, {
                LeagueToaster.notifyOfError(application, "Error in $TAG", it)
            })

    }

    @SuppressLint("CheckResult")
    private fun updateChampData(champDataList: List<ChampData>) {
        Completable.fromAction {
            championGGDao.insertOrUpdateChampData(champDataList)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {
                it
            })
    }

    companion object {
        const val TAG = "ChampDetailBuildsRepo"
    }
}