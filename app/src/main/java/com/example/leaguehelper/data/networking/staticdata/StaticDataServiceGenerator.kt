package com.example.leaguehelper.data.networking.staticdata

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class StaticDataServiceGenerator {

    companion object {
        private const val BASE_URL = "https://ddragon.leagueoflegends.com"

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

        fun createStaticDataAPI(): IStaticDataAPI {
            return getRetrofit()
                .create(IStaticDataAPI::class.java)
        }
    }
}