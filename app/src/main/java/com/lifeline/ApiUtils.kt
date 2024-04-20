package com.lifeline

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ApiUtils {

    suspend fun register(registrationData:RegistrationData):Response?{
        val apiService: APIInterface = APIClient.client!!.create(APIInterface::class.java)
        Log.d("Ntwrk tst", "Starting...")
        return withContext(Dispatchers.IO){
            // Sends getCompanies request to server and records response
            val response = apiService.register(registrationData)
            try {
                // If response is successful, return the contents
                if (response?.isSuccessful != null && response.isSuccessful) {
                    val serverResponse = response.body()
                    Log.d("Ntwrk tst", response.body().toString())
                    return@withContext serverResponse
                }
                // If it was not, throw an exception
                else {
                    Log.d("Ntwrk tst", "Response failed")
                    return@withContext null
                }
            }
            catch (e:Exception){
                Log.e("Ntwrk tst", e.toString())
                return@withContext null
            }
        }
    }
    suspend fun login(){
        val apiService: APIInterface = APIClient.client!!.create(APIInterface::class.java)
        withContext(Dispatchers.IO){
            // Sends getCompanies request to server and records response
            val response = apiService.login("test", "TestPass1", "login")

            try {
                // If response is successful, return the contents
                if (response?.isSuccessful != null && response.isSuccessful) {
                    Log.d("Ntwrk tst", response.body().toString())
                }
                // If it was not, throw an exception
                else {
                    Log.d("Ntwrk tst", "Response failed")
                }
            }
            catch (e:Exception){
                Log.e("Ntwrk tst", e.toString())
            }
        }
    }

}