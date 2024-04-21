package com.lifeline

import android.content.Context
import java.util.Base64

/**
 * Session manager to save and fetch data from SharedPreferences
 */
object SessionManager {
    const val USER_TOKEN = "user_token"

    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String?, context:Context) {
        val prefs = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    /**
     * Function to clear auth token
     */
    fun clearAuthToken(context: Context) {
        val prefs = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.remove(USER_TOKEN)
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(context:Context): String? {
        val prefs = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        return prefs.getString(USER_TOKEN, null)
    }
    fun decodeToken(jwt: String): String {
        val parts = jwt.split(".")
        return try {
            val charset = charset("UTF-8")
            String(Base64.getUrlDecoder().decode(parts[1].toByteArray(charset)), charset)
        } catch (e: Exception) {
            "Error parsing JWT: $e"
        }
    }
}