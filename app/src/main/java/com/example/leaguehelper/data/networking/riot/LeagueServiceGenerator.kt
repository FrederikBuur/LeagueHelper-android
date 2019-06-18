package com.example.leaguehelper.data.networking.riot

import com.google.gson.annotations.SerializedName
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class LeagueServiceGenerator {

    companion object {
        const val API_KEY = "RGAPI-f81d3212-9c81-48af-9a44-00ac19f4882d"
        private const val BASE_URL = "https://euw1.api.riotgames.com"

        fun getBaseUrl(region: Region) = "https://${region.name}.api.riotgames.com" // TODO support different regions

        private var retrofit: Retrofit? = null
        private fun getRetrofit(): Retrofit {
            val log = HttpLoggingInterceptor()
            log.level = HttpLoggingInterceptor.Level.BODY
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(OkHttpClient.Builder()
                        .addInterceptor(log)
                        .addInterceptor { chain ->
                            val original = chain.request()
                            val request = original.newBuilder()
                                .addHeader("X-Riot-Token", API_KEY)
                                .method(original.method(), original.body())
                                .build()
                            chain.proceed(request)
                        }
                        .build()
                    )
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            }
            return retrofit!!

        }

        fun createRiotDataAPI(): IRiotDataAPI {
            return getRetrofit().create(IRiotDataAPI::class.java)
        }
    }

    enum class Region {
        @SerializedName("euw1")
        EUW1,
        @SerializedName("na1")
        NA1,
        @SerializedName("k1")
        KR,
        @SerializedName("ru")
        RU,
        @SerializedName("la1")
        LA1,

    }

}