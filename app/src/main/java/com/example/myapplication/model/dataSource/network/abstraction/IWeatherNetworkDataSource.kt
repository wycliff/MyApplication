package com.example.myapplication.model.dataSource.network.abstraction

import com.example.myapplication.model.dataSource.network.data.response.ErrorResponse
import com.example.myapplication.model.dataSource.network.data.response.GetReasonsResponse
import com.haroldadmin.cnradapter.NetworkResponse

interface IWeatherNetworkDataSource {
    suspend fun getReasons(
        partnerId: String?,
        countryCode: String?
    ): NetworkResponse<GetReasonsResponse, ErrorResponse>
}
