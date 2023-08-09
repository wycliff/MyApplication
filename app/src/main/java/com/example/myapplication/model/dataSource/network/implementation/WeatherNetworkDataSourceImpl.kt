package com.example.myapplication.model.dataSource.network.implementation

import com.example.myapplication.model.dataSource.network.abstraction.IWeatherNetworkDataSource
import com.example.myapplication.model.dataSource.network.apiService.WeatherApiInterface
import com.example.myapplication.model.dataSource.network.data.response.ErrorResponse
import com.example.myapplication.model.dataSource.network.data.response.GetReasonsResponse
import com.haroldadmin.cnradapter.NetworkResponse
import javax.inject.Inject

class WeatherNetworkDataSourceImpl @Inject constructor(
    private val apiInterface: WeatherApiInterface,
) : IWeatherNetworkDataSource {
    override suspend fun getReasons(
        partnerId: String?,
        countryCode: String?,
    ): NetworkResponse<GetReasonsResponse, ErrorResponse> {
        return apiInterface.getReasons(partnerId = partnerId, countryCode = countryCode)
    }
}