package com.naemo.afriscout.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject


class NetworkUtils {

    val url: String = "https://www.google.com"

    @Inject
    constructor()

    fun isNetworkConnected(context: Context): Boolean {
            try {
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.setRequestProperty("User-Agent", "Test")
                connection.setRequestProperty("Connection", "close")
                connection.connectTimeout = 1000
                connection.connect()
                Log.d("Network Utils", "hasInternetConnected: ${(connection.responseCode == 200)}")
                return (connection.responseCode == 200)
            } catch (e: IOException) {
                Log.e("Network Utils", "Error checking internet connection", e)
            }

        Log.d("Network Utils", "hasInternetConnected: false")
        return false
    }

}




