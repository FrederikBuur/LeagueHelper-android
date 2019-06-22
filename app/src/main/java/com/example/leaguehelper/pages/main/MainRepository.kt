package com.example.leaguehelper.pages.main

import com.example.leaguehelper.data.dao.ChampionDao
import com.example.leaguehelper.data.dao.ConfigDataDao
import com.example.leaguehelper.data.networking.staticdata.StaticDataServiceGenerator
import com.example.leaguehelper.data.networking.staticdata.IStaticDataAPI
import com.example.leaguehelper.models.ConfigData
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class MainRepository(
    private val configDataDao: ConfigDataDao,
    private val championDao: ChampionDao
) {

    private var staticDataAPI: IStaticDataAPI = StaticDataServiceGenerator.createStaticDataAPI()

    val configData = configDataDao.getConfigData()

    fun checkForUpdates(language: String): Observable<Unit> {

        val localVersion = configData.value
        //get remote newest version
        return staticDataAPI.getVersions()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .concatMap { remoteVersions ->
                // is update needed
                val newVersion = remoteVersions.first()
                return@concatMap if (localVersion?.isOlderThan(newVersion) != false) {
                    // update localVersion
                    val config = ConfigData(newVersion, System.currentTimeMillis(), configData.value?.summoner)
                    configDataDao.insertOrUpdateConfigData(config)
                    // update champions form remote
                    staticDataAPI.getChampions(newVersion, language)
                } else {
                    // update not needed
                    Observable.empty()
                }
            }
            .concatMap { championResponse ->
                //save all champions in room
                val champions = championResponse.data.values.toList()
                return@concatMap Observable.fromCallable {
                    championDao.insertOrUpdateChampions(champions)
                }
            }
    }

}