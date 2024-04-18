package com.lifeline

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object APIClient {
    private var retrofit: Retrofit? = null
    val client: Retrofit?
        get() {
            retrofit = Retrofit.Builder()
                .baseUrl("https://lifeline-project.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit
        }
}