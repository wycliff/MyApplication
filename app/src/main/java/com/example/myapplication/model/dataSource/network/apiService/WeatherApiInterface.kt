package com.example.myapplication.model.dataSource.network.apiService

import com.example.myapplication.model.dataSource.network.data.response.ErrorResponse
import com.example.myapplication.model.dataSource.network.data.response.GetReasonsResponse
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApiInterface {
    @GET("partners/{partnerId}/orders/waypoint/failure-reasons")
    suspend fun getReasons(
        @Path("partnerId") partnerId: String?,
        @Query("countryCode") countryCode: String?,
    ): NetworkResponse<GetReasonsResponse, ErrorResponse>
}