package com.mattis.myapplication

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL


object NetworkHelper {
    suspend fun makeGetRequest(urlString: String): Pair<Int, String> {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 5000
                connection.readTimeout = 5000

                val responseCode = connection.responseCode

                val responseBody = if (responseCode in 200..299) {
                    connection.inputStream.bufferedReader().use { it.readText() }
                } else {
                    connection.errorStream?.bufferedReader()?.use { it.readText() } ?: "Unknown error"
                }

                responseCode to responseBody  // Return correct HTTP status and message
            } catch (e: Exception) {
                Log.e("NetworkError", "Failed to fetch: ${e.message}", e)
                -1 to "Network error: ${e.message}"  // Return -1 for network failures
            }
        }
    }
}

