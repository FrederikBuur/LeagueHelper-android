package com.example.leaguehelper.data.networking.riot

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class LeagueServiceGenerator {

    companion object {
        const val API_KEY = "RGAPI-af93790c-8b9c-4bba-8985-13e9eef63aa1"
        const val BASE_URL = "https://euw1.api.riotgames.com"

        private var retrofit: Retrofit? = null
        private fun getRetrofit(): Retrofit {
            val log = HttpLoggingInterceptor()
            log.level = HttpLoggingInterceptor.Level.BODY
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(OkHttpClient.Builder().addInterceptor(log).build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            }
            return retrofit!!

        }

//        fun createStaticDataAPI(): IStaticData {
//            return getRetrofit().create(IStaticData::class.java)
//        }
    }
}