package com.ycliffe.myapplication.model.dataSource.network.data.response

data class FiveDayWeather(
    val city: City?,
    val cnt: Int?,
    val cod: Int?,
    val list: List<CurrentWeather>?,
    val message: Int?
)

data class City(
    val coord: Coord?,
    val country: String?,
    val id: Int?,
    val name: String?,
    val population: Int?,
    val sunrise: Int?,
    val sunset: Int?,
    val timezone: Int?
)
