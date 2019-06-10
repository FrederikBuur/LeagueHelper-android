package com.example.leaguehelper.util.typeconverter

import androidx.room.TypeConverter
import com.example.leaguehelper.models.match.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object MatchTypeConverter {

    //region team type converter
    @JvmStatic
    @TypeConverter
    fun stringToTeams(json: String): List<Team> {
        val gson = Gson()
        val type = object : TypeToken<List<Team>>() {

        }.type
        return gson.fromJson(json, type)
    }

    @JvmStatic
    @TypeConverter
    fun teamsToString(list: List<Team>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Team>>() {

        }.type
        return gson.toJson(list, type)
    }
    //endregion

    //region participant type converter
    @JvmStatic
    @TypeConverter
    fun stringToParticipants(json: String): List<Participant> {
        val gson = Gson()
        val type = object : TypeToken<List<Participant>>() {

        }.type
        return gson.fromJson(json, type)
    }

    @JvmStatic
    @TypeConverter
    fun participantsToString(list: List<Participant>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Participant>>() {

        }.type
        return gson.toJson(list, type)
    }
    //endregion

    //region participant identity type converter
    @JvmStatic
    @TypeConverter
    fun stringToParticipantIdentities(json: String): List<ParticipantIdentity> {
        val gson = Gson()
        val type = object : TypeToken<List<ParticipantIdentity>>() {

        }.type
        return gson.fromJson(json, type)
    }

    @JvmStatic
    @TypeConverter
    fun participantIdentitiesToString(list: List<ParticipantIdentity>): String {
        val gson = Gson()
        val type = object : TypeToken<List<ParticipantIdentity>>() {

        }.type
        return gson.toJson(list, type)
    }
    //endregion

}