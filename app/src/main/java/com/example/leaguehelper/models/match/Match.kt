package com.example.leaguehelper.models.match

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.leaguehelper.util.typeconverter.EnumTypeConverter
import com.example.leaguehelper.util.typeconverter.MatchTypeConverter

@Entity(tableName = "match_table")
@TypeConverters(MatchTypeConverter::class, EnumTypeConverter::class)
data class Match(
    @PrimaryKey
    val gameId: Long,
    val platformId: String,
    val gameCreation: Long,
    val gameDuration: Int,
    val queueId: QueueType,
    val teams: List<Team>,
    val participants: List<Participant>,
    val participantIdentities: List<ParticipantIdentity>
)

data class Team(
    val teamId: TeamColor,
    val win: String,
    val towerKills: Int,
    val inhibitorKills: Int,
    val baronKills: Int,
    val dragonKills: Int,
    val bans: List<Ban>
)

data class Ban(
    val championId: Int,
    val pickTurn: Int
)

data class Participant(
    val participantId: Int,
    val teamId: Int,
    val championId: Int,
    val spell1Id: Int,
    val spell2Id: Int,
    val stats: Stats
)

data class Stats(
    val win: Boolean,
    val kills: Int,
    val deaths: Int,
    val assists: Int,
    val Item0: Int,
    val Item1: Int,
    val Item2: Int,
    val Item3: Int,
    val Item4: Int,
    val Item5: Int,
    val Item6: Int
)

data class ParticipantIdentity(
    val participantId: Int,
    val player: Player
)

data class Player(
    val platformId: String,
    val accountId: String,
    val summonerName: String,
    val profileIcon: Int
)