package com.example.leaguehelper.util.typeconverter

import androidx.room.TypeConverter
import com.example.leaguehelper.models.match.QueueType
import com.example.leaguehelper.models.match.TeamColor
import com.example.leaguehelper.models.match.TeamResult

object EnumTypeConverter {

    //region queue type converter
    @JvmStatic
    @TypeConverter
    fun fromQueueTypeToString(queueType: QueueType?): String {
        return queueType?.toString() ?: run { QueueType.DEFAULT.toString() }
    }

    @JvmStatic
    @TypeConverter
    fun fromStringToQueueType(queueType: String): QueueType {
        return if (queueType.isEmpty()) {
            QueueType.DEFAULT
        } else {
            QueueType.valueOf(queueType)
        }
    }
    //endregion

    //region team color type converter
    @JvmStatic
    @TypeConverter
    fun fromTeamColorToString(teamColor: TeamColor?): String {
        return teamColor?.toString() ?: run { TeamColor.DEFAULT.toString() }
    }

    @JvmStatic
    @TypeConverter
    fun fromStringToTeamColor(queueType: String): TeamColor {
        return if (queueType.isEmpty()) {
            TeamColor.DEFAULT
        } else {
            TeamColor.valueOf(queueType)
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromTeamResultToString(teamResult: TeamResult?): String {
        return teamResult?.toString() ?: run { TeamColor.DEFAULT.toString() }
    }

    @JvmStatic
    @TypeConverter
    fun fromStringToTeamResult(teamResult: String): TeamResult {
        return if (teamResult.isEmpty()) {
            TeamResult.DEFAULT
        } else {
            TeamResult.valueOf(teamResult)
        }
    }

}