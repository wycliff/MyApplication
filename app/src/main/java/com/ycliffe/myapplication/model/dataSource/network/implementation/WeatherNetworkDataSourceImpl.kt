package com.ycliffe.myapplication.model.dataSource.network.implementation

import com.ycliffe.myapplication.BuildConfig
import com.ycliffe.myapplication.model.dataSource.network.abstraction.IWeatherNetworkDataSource
import com.ycliffe.myapplication.model.dataSource.network.apiService.WeatherApiInterface
import com.ycliffe.myapplication.model.dataSource.network.data.response.CurrentWeather
import com.ycliffe.myapplication.model.dataSource.network.data.response.ErrorResponse
import com.ycliffe.myapplication.model.dataSource.network.data.response.FiveDayWeather
import com.ycliffe.myapplication.utils.Constants.Companion.METRIC
import com.haroldadmin.cnradapter.NetworkResponse
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
            units = METRIC
        )
    }
}