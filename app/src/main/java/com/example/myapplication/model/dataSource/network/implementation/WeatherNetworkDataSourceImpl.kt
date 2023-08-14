package com.example.myapplication.model.dataSource.network.implementation

import com.example.myapplication.BuildConfig
import com.example.myapplication.model.dataSource.network.abstraction.IWeatherNetworkDataSource
import com.example.myapplication.model.dataSource.network.apiService.WeatherApiInterface
import com.example.myapplication.model.dataSource.network.data.response.CurrentWeather
import com.example.myapplication.model.dataSource.network.data.response.ErrorResponse
import com.example.myapplication.model.dataSource.network.data.response.FiveDayWeather
import com.example.myapplication.utils.Constants.Companion.METRIC
import com.haroldadmin.cnradapter.NetworkResponse
import timber.log.Timber
import javax.inject.Inject

class WeatherNetworkDataSourceImpl @Inject constructor(
    private val apiInterface: WeatherApiInterface,
) : IWeatherNetworkDataSource {

    override suspend fun getCurrentWeather(
        lat: String?,
        long: String?
    ): NetworkResponse<CurrentWeather, ErrorResponse> {
        return apiInterface.getCurrentWeather(
            lat = lat,
            long = long,
            appId = BuildConfig.API_KEY,
            units = METRIC
        )
    }

    override suspend fun getFiveDayWeather(
        lat: String?,
        long: String?
    ): NetworkResponse<FiveDayWeather, ErrorResponse> {
        return apiInterface.getFiveDayWeather(
            lat = lat,
            long = long,
            appId = BuildConfig.API_KEY,
            units = METRIC,
            count = 20
        )
    }
}