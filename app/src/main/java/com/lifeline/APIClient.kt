package com.lifeline

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object APIClient {
    private var retrofit: Retrofit? = null
    val client: Retrofit?
        get() {
            retrofit = Retrofit.Builder()
                .baseUrl("https://lifeline-project.net:5000/") // Lifeline's url
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit
        }
}