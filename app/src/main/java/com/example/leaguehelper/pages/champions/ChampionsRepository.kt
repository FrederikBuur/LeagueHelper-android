package com.example.leaguehelper.pages.champions

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.leaguehelper.R
import com.example.leaguehelper.data.LeagueHelperDatabase
import com.example.leaguehelper.data.SessionController
import com.example.leaguehelper.data.dao.ChampionDao
import com.example.leaguehelper.models.staticdata.champion.Champion
import com.example.leaguehelper.data.networking.staticdata.StaticDataServiceGenerator
import com.example.leaguehelper.data.networking.staticdata.IStaticDataAPI
import com.example.leaguehelper.util.LeagueToaster
import com.trello.rxlifecycle3.android.FragmentEvent
import com.trello.rxlifecycle3.components.support.RxFragment
import com.trello.rxlifecycle3.kotlin.bindUntilEvent
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ChampionsRepository(private val application: Application) {

    private var championDao: ChampionDao
    private var staticDataAPI: IStaticDataAPI

    private var champions: LiveData<List<Champion>?>

    init {
        val database = LeagueHelperDatabase.getInstance(application)
        championDao = database.championDao()

        staticDataAPI = StaticDataServiceGenerator.createStaticDataAPI()

        champions = championDao.getAllChampions()
    }

    fun getChampions(): LiveData<List<Champion>?> {
        return champions
    }

    fun fetchChampions(fragment: RxFragment) {
        val language = application.getString(R.string.static_data_language_code)

        SessionController.getInstance().configData?.version?.let { version ->
            staticDataAPI.getChampions(version, language)
                .bindUntilEvent(fragment, FragmentEvent.STOP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ championsResponse ->
                    // saves champions into room
                    val champions = championsResponse.data.values.toList()
                    updateChampions(champions)
                }, {
                    LeagueToaster.notifyOfError(application, "Error in ${ChampionsRepository.TAG}", it)
                })
        } ?: kotlin.run {
            Log.d(TAG, "Version in session controller is null")
        }
    }

    @SuppressLint("CheckResult")
    private fun updateChampions(champions: List<Champion>) {
        Completable.fromAction {
            championDao.insertOrUpdateChampions(champions)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                Log.d(TAG, "Updating local champions")
            }
            .subscribe({}, {})
    }

    companion object {
        const val TAG = "MainRepository"
    }
}