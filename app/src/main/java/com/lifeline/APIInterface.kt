package com.lifeline

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface APIInterface {
    @FormUrlEncoded
    @POST("/verifyLoginCreate.php")
    suspend fun register(
        @Field("accType") accType: Int,
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("id") id: Int,
        @Field("password1") password1: String,
        @Field("password2") password2: String,
        @Field("submit") action: String
        ): Response<String?>? //
    @FormUrlEncoded
    @POST("/verifyLoginCreate.php")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("submit") action: String
        ): Response<String?>? // If we get a response then the login failed

}