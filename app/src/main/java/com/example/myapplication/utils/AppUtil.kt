package com.example.myapplication.utils

import android.app.Activity
import android.content.Context
import android.location.LocationManager
import timber.log.Timber
import kotlin.math.roundToInt

object AppUtil {
    fun hasGps(activity: Activity): Boolean {
        val locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var gpsEnabled = false
        var networkEnabled = false

        try {
            gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (e: Exception) {
            Timber.w(e)
        }

        try {
            networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (e: Exception) {
            Timber.w(e)
        }

        return gpsEnabled && networkEnabled
    }

    fun roundDown(double: Double): Double {
        return (double * 1000).roundToInt().toDouble() / 1000
    }
}