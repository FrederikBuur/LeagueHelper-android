package com.example.leaguehelper.data.networking.championgg

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ChampionGGServiceGenerator {

    companion object {
        private const val BASE_URL = "https://api.champion.gg"
        const val API_KEY = "17a39a1aa56f8b5662316269f47cea92"

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

        fun createChampionGGAPI(): IChampionGGAPI {
            return getRetrofit()
                .create(IChampionGGAPI::class.java)
        }
    }
}