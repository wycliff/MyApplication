package com.example.myapplication.model.repository.abstraction

import com.example.myapplication.model.dataSource.network.data.response.CurrentWeather
import com.example.myapplication.model.dataSource.network.data.response.ErrorResponse
import com.example.myapplication.model.dataSource.network.data.response.FiveDayWeather
import com.example.myapplication.model.dataSource.network.data.response.GetReasonsResponse
import com.haroldadmin.cnradapter.NetworkResponse

interface IWeatherRepository {
    suspend fun getCurrentWeather(
        lat: String?,
        long: String?
    ): NetworkResponse<CurrentWeather, ErrorResponse>

    suspend fun getFiveDayWeather(
        lat: String?,
        long: String?,
    ): NetworkResponse<FiveDayWeather, ErrorResponse>
}