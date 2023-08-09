package com.example.myapplication.model.repository.implementation

import com.example.myapplication.model.dataSource.network.abstraction.IWeatherNetworkDataSource
import com.example.myapplication.model.dataSource.network.data.response.ErrorResponse
import com.example.myapplication.model.dataSource.network.data.response.GetReasonsResponse
import com.example.myapplication.model.repository.abstraction.IWeatherRepository
import com.haroldadmin.cnradapter.NetworkResponse
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherNetworkDataSource: IWeatherNetworkDataSource,

    ) : IWeatherRepository {
    override suspend fun getReasons(
        partnerId: String?,
        countryCode: String?
    ): NetworkResponse<GetReasonsResponse, ErrorResponse> {
        return weatherNetworkDataSource.getReasons(partnerId,countryCode)
    }
}