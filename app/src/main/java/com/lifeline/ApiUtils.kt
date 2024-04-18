package com.lifeline

import android.util.Log
import com.lifeline.APIClient.client
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ApiUtils {

    suspend fun register(){
        val apiService: APIInterface = APIClient!!.client!!.create(APIInterface::class.java)
        withContext(Dispatchers.IO){
            // Sends getCompanies request to server and records response
            val response = apiService.register(1, "cb2", "cbeatty@gmail.com", 83, "pass", "pass", "createAccount")

            // If response is successful, return the contents
            if (response?.isSuccessful != null && response!!.isSuccessful) {
                Log.d("Ntwrk tst", response.body().toString())
            }
            // If it was not, throw an exception
            else{
                Log.d("Ntwrk tst", "Response failed")
            }
        }
    }
}