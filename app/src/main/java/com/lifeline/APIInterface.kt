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
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("submit") action: String
        ): Response<String?>? // If we get a response then the login failed
    @GET("/test")
    suspend fun test(): Response<com.lifeline.Response>? // If we get a response then the test failed

}