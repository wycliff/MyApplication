package com.ycliffe.myapplication.model.dataSource.network.abstraction

import com.ycliffe.myapplication.model.dataSource.network.data.response.CurrentWeather
import com.ycliffe.myapplication.model.dataSource.network.data.response.ErrorResponse
import com.ycliffe.myapplication.model.dataSource.network.data.response.FiveDayWeather
import com.haroldadmin.cnradapter.NetworkResponse

interface IWeatherNetworkDataSource {
    suspend fun getCurrentWeather(
        lat: String?,
        long: String?,
    ): NetworkResponse<CurrentWeather, ErrorResponse>


    suspend fun getFiveDayWeather(
        lat: String?,
        long: String?
    ): NetworkResponse<FiveDayWeather, ErrorResponse>

}
