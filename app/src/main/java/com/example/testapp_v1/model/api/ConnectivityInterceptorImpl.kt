package com.example.testapp_v1.model.api

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Response

@Suppress("DEPRECATION")
class ConnectivityInterceptorImpl(val context: Context) : ConnectivityInterceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isOnline())
            throw NoConnectivityException()
        return chain.proceed(chain.request())
    }

    private fun isOnline(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}