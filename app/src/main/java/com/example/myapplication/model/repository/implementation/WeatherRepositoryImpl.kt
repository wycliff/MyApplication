package com.example.myapplication.model.repository.implementation

import com.example.myapplication.model.dataSource.network.abstraction.IWeatherNetworkDataSource
import com.example.myapplication.model.dataSource.network.data.response.CurrentWeather
import com.example.myapplication.model.dataSource.network.data.response.ErrorResponse
import com.example.myapplication.model.dataSource.network.data.response.FiveDayWeather
import com.example.myapplication.model.repository.abstraction.IWeatherRepository
import com.haroldadmin.cnradapter.NetworkResponse
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherNetworkDataSource: IWeatherNetworkDataSource,
) : IWeatherRepository {
    override suspend fun getCurrentWeather(
        lat: String?,
        long: String?
    ): NetworkResponse<CurrentWeather, ErrorResponse> {
        return weatherNetworkDataSource.getCurrentWeather(
            lat = lat,
            long = long,
        )
    }

    override suspend fun getFiveDayWeather(
        lat: String?,
        long: String?
    ): NetworkResponse<FiveDayWeather, ErrorResponse> {
        return weatherNetworkDataSource.getFiveDayWeather(
            lat = lat,
            long = long,
        )
    }
}