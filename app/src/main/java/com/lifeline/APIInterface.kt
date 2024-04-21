package com.lifeline

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import java.math.BigInteger


interface APIInterface {
    @POST("/createAccount")
    suspend fun register(
        @Body registrationObject:RegistrationData
        ): Response<LoginResponse>?
    @POST("/login")
    suspend fun login(
        @Body loginObject:LoginData
    ): Response<LoginResponse>?
    @GET("/studentInfo")
    suspend fun searchId(
        @Header("Authorization") token: String?,
        @Query("studentId") studentId:BigInteger
    ): Response<StudentInfo>?
    @POST("/studentInfo")
    suspend fun updateStudentInfo(
        @Header("Authorization") token: String?,
        @Body studentInfo:StudentInfo
    ): Response<com.lifeline.Response>?

}