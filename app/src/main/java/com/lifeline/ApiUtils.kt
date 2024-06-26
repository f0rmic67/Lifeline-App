package com.lifeline

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.math.BigInteger

object ApiUtils {

    suspend fun register(registrationData:RegistrationData, context: Context):LoginResponse?{
        val apiService: APIInterface = APIClient.client!!.create(APIInterface::class.java)
        Log.d("Register", "Starting...")
        return withContext(Dispatchers.IO){
            try {
                // Sends getCompanies request to server and records response
                val response = apiService.register(registrationData)
                // If response is successful, return the contents
                if (response?.isSuccessful != null && response.isSuccessful) {
                    val serverResponse = response.body()
                    if(serverResponse?.responseCode == 200){
                        serverResponse.token?.let { SessionManager.saveAuthToken(it, context) }
                    }
                    Log.d("Register", response.body().toString())
                    return@withContext serverResponse
                }
                // If it was not, throw an exception
                else {
                    Log.d("Register", "Response failed")
                    return@withContext null
                }
            }
            catch (e:Exception){
                Log.e("Ntwrk tst", e.toString())
                return@withContext null
            }
        }
    }
    suspend fun login(loginData:LoginData, context: Context):LoginResponse?{
        val apiService: APIInterface = APIClient.client!!.create(APIInterface::class.java)
        Log.d("Login", "Starting...")
        return withContext(Dispatchers.IO){
            try {
                // Sends getCompanies request to server and records response
                val response = apiService.login(loginData)
                // If response is successful, return the contents
                if (response?.isSuccessful != null && response.isSuccessful) {
                    val serverResponse = response.body()
                    if(serverResponse?.responseCode == 200){
                        serverResponse.token?.let { SessionManager.saveAuthToken(it, context) }
                    }
                    Log.d("Login", response.body().toString())
                    return@withContext serverResponse
                }
                // If it was not, throw an exception
                else {
                    Log.d("Login", "Response failed")
                    return@withContext null
                }
            }
            catch (e:Exception){
                Log.e("Ntwrk tst", e.toString())
                return@withContext null
            }
        }
    }
    suspend fun searchStudentId(studentId:BigInteger, context: Context):StudentInfo?{
        val apiService: APIInterface = APIClient.client!!.create(APIInterface::class.java)
        return withContext(Dispatchers.IO){
            try {
                // Sends getCompanies request to server and records response
                val savedToken:String? = SessionManager.fetchAuthToken(context)
                Log.d("Search ID", "Using saved token $savedToken")
                val response = apiService.searchId(savedToken, studentId)
                // If response is successful, return the contents
                if (response?.isSuccessful != null && response.isSuccessful) {
                    val serverResponse = response.body()
                    Log.d("Search ID", response.body().toString())
                    return@withContext serverResponse
                }
                // If it was not, throw an exception
                else {
                    Log.d("Search ID", "Response failed")
                    return@withContext null
                }
            }
            catch (e:Exception){
                Log.e("Ntwrk tst", e.toString())
                return@withContext null
            }
        }
    }
    suspend fun updateStudentInfo(studentInfo: StudentInfo, context: Context):Response?{
        val apiService: APIInterface = APIClient.client!!.create(APIInterface::class.java)
        Log.d("UpdateStudentInfo", "Starting...")
        return withContext(Dispatchers.IO){
            try {
                val savedToken:String? = SessionManager.fetchAuthToken(context)
                // Sends getCompanies request to server and records response
                val response = apiService.updateStudentInfo(savedToken, studentInfo)
                // If response is successful, return the contents
                if (response?.isSuccessful != null && response.isSuccessful) {
                    val serverResponse = response.body()
                    Log.d("UpdateStudentInfo", serverResponse?.message.toString())
                    return@withContext serverResponse
                }
                // If it was not, throw an exception
                else {
                    Log.d("UpdateStudentInfo", "Response failed")
                    return@withContext null
                }
            }
            catch (e:Exception){
                Log.e("Ntwrk tst", e.toString())
                return@withContext null
            }
        }
    }

    suspend fun getRecentSearches(context: Context):List<SearchData>?{
        val apiService: APIInterface = APIClient.client!!.create(APIInterface::class.java)
        return withContext(Dispatchers.IO){
            // Sends getCompanies request to server and records response
            try {
                val savedToken:String? = SessionManager.fetchAuthToken(context)
                Log.d("Recent Searches", "Using saved token $savedToken")
                val response = apiService.getRecentSearches(savedToken)
                // If response is successful, return the contents
                if (response?.isSuccessful != null && response.isSuccessful) {
                    val serverResponse = response.body()
                    Log.d("Recent Searches", response.body().toString())
                    return@withContext serverResponse
                }
                // If it was not, throw an exception
                else {
                    Log.d("Recent Searches", "Response failed")
                    return@withContext null
                }
            }
            catch (e:Exception){
                Log.e("Recent Searches", e.toString())
                return@withContext null
            }
        }
    }

}