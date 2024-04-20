package com.lifeline

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface APIInterface {
    @POST("/createAccount")
    suspend fun register(
        @Body registrationObject:RegistrationData
        ): Response<com.lifeline.Response>?
    @POST("/login")
    suspend fun login(
        @Body loginObject:LoginData
    ): Response<com.lifeline.Response>?

}