package com.example.myapplication.utils

class Constants {
    companion object {
        //Units
        const val METRICS = "metrics"

        //Main Weather
        const val CLOUDY = "Clouds"
        const val SUNNY = "Clear"
        const val RAINY = "Rain"

        //location
        const val REQUEST_CHECK_SETTINGS = 0x1
        const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000
        const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2
    }
}