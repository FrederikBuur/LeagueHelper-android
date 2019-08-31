package com.example.leaguehelper.pages.profile

import com.example.leaguehelper.data.dao.ChampionDao
import com.example.leaguehelper.data.dao.ConfigDataDao
import com.example.leaguehelper.data.dao.MatchDao
import com.example.leaguehelper.data.networking.riot.LeagueServiceGenerator
import com.example.leaguehelper.models.ConfigData
import com.example.leaguehelper.models.match.Match
import com.example.leaguehelper.models.match.MatchesResponse
import com.example.leaguehelper.models.staticdata.champion.Champion
import com.example.leaguehelper.models.summoner.Summoner
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers

class ProfileRepository(
    private val matchDao: MatchDao,
    private val championDao: ChampionDao,
    private val configDataDao: ConfigDataDao
) {
    private val riotDataAPI = LeagueServiceGenerator.createRiotDataAPI()

    val matches = matchDao.getAllMatches()

    suspend fun fetchMatches(accountId: String, beginIndex: Int, endIndex: Int): List<Match> {
        return riotDataAPI.getMatchesMetaData(accountId, beginIndex, endIndex)
            .matches.map { matchMetaData ->
            riotDataAPI.getMatchById(matchMetaData.gameId)
        }
    }

//    fun fetchMatches1(accountId: String, beginIndex: Int, endIndex: Int): Observable<*> {
//        val m = ArrayList<Match>()
//        return riotDataAPI.getMatchesMetaData(accountId, beginIndex, endIndex)
//            .subscribeOn(Schedulers.io())
//            .observeOn(Schedulers.io())
//            .flatMap { matchesResponse ->
//                return@flatMap Observable.fromIterable(matchesResponse.matches)
//                    .concatMap { matchMetaData ->
//                        riotDataAPI.getMatchById(matchMetaData.gameId)
//                    }
//            }
//            .concatMap { match ->
//                Observable.fromCallable {
//                    m.add(match)
//                }
//            }
//            .doOnComplete {
//                matchDao.insertOrUpdateMatches(m)
//            }
//    }

    suspend fun fetchSummonerByName(name: String): Summoner {
        return riotDataAPI.getSummonerByName(name)
    }

    suspend fun getChampionByKey(id: Int): Champion? {
        return championDao.getChampionSuspend(id)
    }

    suspend fun getConfigData(): ConfigData? {
        return configDataDao.getConfigDataSuspend()
    }

}