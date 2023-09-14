package com.ycliffe.myapplication.model.repository.implementation

import com.ycliffe.myapplication.model.dataSource.network.abstraction.IWeatherNetworkDataSource
import com.ycliffe.myapplication.testFake.getFakeCoordEntity
import com.ycliffe.myapplication.testFake.getFakeCurrentWeatherResponse
import com.ycliffe.myapplication.testFake.getFiveDayWeatherResponse
import com.haroldadmin.cnradapter.NetworkResponse
import com.haroldadmin.cnradapter.invoke
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach


internal class WeatherRepositoryImplTest {

    private val weatherNetworkDataSource: IWeatherNetworkDataSource = mockk()

    private val repository = WeatherRepositoryImpl(
        weatherNetworkDataSource = weatherNetworkDataSource
    )

    @BeforeEach
    fun setUp() {
        clearAllMocks()
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun `get current weather success`() = runBlocking {
        val coordinateItem = getFakeCoordEntity()
        val weatherResponse = getFakeCurrentWeatherResponse()

        coEvery {
            weatherNetworkDataSource.getCurrentWeather(
                lat = coordinateItem.lat.toString(),
                long = coordinateItem.lon.toString()
            )
        } returns NetworkResponse.Success(weatherResponse, null, 200)

        val result = repository.getCurrentWeather(
            coordinateItem.lat.toString(),
            coordinateItem.lon.toString()
        ).invoke()?.main

        assertEquals(result, weatherResponse.main)

        coVerify {
            weatherNetworkDataSource.getCurrentWeather(
                coordinateItem.lat.toString(),
                coordinateItem.lon.toString()
            )
        }
    }

    @Test
    fun `get five day weather success`() = runBlocking {
        val coordinateItem = getFakeCoordEntity()
        val fiveDayWeatherResponse = getFiveDayWeatherResponse()

        coEvery {
            weatherNetworkDataSource.getFiveDayWeather(
                lat = coordinateItem.lat.toString(),
                long = coordinateItem.lon.toString()
            )
        } returns NetworkResponse.Success(fiveDayWeatherResponse, null, 200)

        val result = repository.getFiveDayWeather(
            coordinateItem.lat.toString(),
            coordinateItem.lon.toString()
        ).invoke()?.city

        assertEquals(result, fiveDayWeatherResponse.city)

        coVerify {
            weatherNetworkDataSource.getFiveDayWeather(
                coordinateItem.lat.toString(),
                coordinateItem.lon.toString()
            )
        }
    }
}