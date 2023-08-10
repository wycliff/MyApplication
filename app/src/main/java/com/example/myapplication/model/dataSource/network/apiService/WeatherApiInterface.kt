package com.example.myapplication.model.dataSource.network.apiService

import com.example.myapplication.model.dataSource.network.data.response.CurrentWeather
import com.example.myapplication.model.dataSource.network.data.response.ErrorResponse
import com.example.myapplication.model.dataSource.network.data.response.FiveDayWeather
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

    @GET("forecast")
    suspend fun getCurrentWeather(
        @Query("lat") lat: String?,
        @Query("lon") long: String?,
        @Query("appid") appId: String?,
        @Query("units") units: String?,
    ): NetworkResponse<CurrentWeather, ErrorResponse>

    @GET("forecast")
    suspend fun getFiveDayWeather(
        @Query("lat") lat: String?,
        @Query("lon") long: String?,
        @Query("appid") appId: String?,
        @Query("units") units: String?,
        @Query("cnt") count: Int?,
    ): NetworkResponse<FiveDayWeather, ErrorResponse>
}