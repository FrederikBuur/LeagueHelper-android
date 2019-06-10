package com.example.leaguehelper.pages.profile

import com.example.leaguehelper.data.dao.MatchDao
import com.example.leaguehelper.data.networking.riot.LeagueServiceGenerator
import com.example.leaguehelper.models.match.Match
import com.example.leaguehelper.models.match.MatchesResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers

class ProfileRepository(
    private val matchDao: MatchDao
) {

    private val tempAcc = "QBP6sESg5Y_gIhjQfjf-yK-LJbMHjV-b1oM9mzL67-0"

    private val riotDataAPI = LeagueServiceGenerator.createRiotDataAPI()

    val matches = matchDao.getAllMatches()

    fun fetchMatches(accountId: String = tempAcc, beginIndex: Int, endIndex: Int): Observable<*> {
        val m = ArrayList<Match>()
        return riotDataAPI.getMatchesMetaData(accountId, beginIndex, endIndex)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .flatMap { matchesResponse ->
                return@flatMap Observable.fromIterable(matchesResponse.matches)
                    .concatMap { matchMetaData ->
                        riotDataAPI.getMatchById(matchMetaData.gameId)
                    }
            }
            .concatMap { match ->
                Observable.fromCallable {
                    m.add(match)
                }
            }
            .doOnComplete {
                matchDao.insertOrUpdateMatches(m)
            }
    }

}