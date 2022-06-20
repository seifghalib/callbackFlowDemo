package com.example.cognizantdemo

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@OptIn(ExperimentalCoroutinesApi::class)
fun Context.isNetworkAvailable(): Flow<Boolean> = callbackFlow {

    val callback = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            trySend(true)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            trySend(false)
        }
    }

    val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

    connectivityManager.registerNetworkCallback(
        NetworkRequest.Builder()
            .addCapability(NET_CAPABILITY_INTERNET)
            .build(), callback)

    awaitClose {
        connectivityManager.unregisterNetworkCallback(callback)
    }
}