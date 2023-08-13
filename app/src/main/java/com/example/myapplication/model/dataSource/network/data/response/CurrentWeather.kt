package com.example.myapplication.model.dataSource.network.data.response

import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    @SerializedName("cod")
    val cod: Int?,
    @SerializedName("coord")
    val coord: Coord?,
    @SerializedName("dt")
    val dt: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("main")
    val main: Main?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("timezone")
    val timezone: Int?,
    @SerializedName("weather")
    val weather: List<Weather?>?,
    @SerializedName("dt_txt")
    val date: String?
)

data class Coord(
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("lon")
    val lon: Double?
)