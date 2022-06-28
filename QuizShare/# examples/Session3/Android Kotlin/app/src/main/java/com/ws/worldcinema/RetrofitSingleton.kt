package com.ws.worldcinema

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitSingleton internal constructor() {
    private val instance: Retrofit
    var service: ApiService

    companion object {
        var tokenWithoutBarrier: String? = null
            private set

        fun getToken(): String {
            return "Bearer " + tokenWithoutBarrier
        }

        fun setToken(token: String?) {
            tokenWithoutBarrier = token
        }
    }

    init {
        val logging = HttpLoggingInterceptor()
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
        // add your other interceptors …

// add logging as last interceptor
        httpClient.addInterceptor(logging) // <-- this is the important line!
        instance = Retrofit.Builder()
                .baseUrl("http://cinema.areas.su/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
        service = instance.create(ApiService::class.java)
    }
}