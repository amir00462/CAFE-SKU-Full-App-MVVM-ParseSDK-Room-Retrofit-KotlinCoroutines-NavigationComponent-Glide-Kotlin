package com.ahm.coronam.androidWrapper

import android.content.Context
import android.net.ConnectivityManager

class NetWorkChecker(private val context:Context) {

    val checkNetwork :Boolean
    get() {
        val conManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val netInfo = conManager.activeNetworkInfo

        return netInfo != null && netInfo.isConnected
    }

}