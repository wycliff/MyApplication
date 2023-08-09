package com.example.myapplication.di

import com.example.myapplication.model.dataSource.network.abstraction.IWeatherNetworkDataSource
import com.example.myapplication.model.dataSource.network.apiService.WeatherApiInterface
import com.example.myapplication.model.dataSource.network.implementation.WeatherNetworkDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    @Provides
    @Singleton
    fun provideReasonsApiService(
        retrofit: Retrofit,
    ): WeatherApiInterface = retrofit.create(WeatherApiInterface::class.java)
}

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class MainBindingModule {
    @Binds
    abstract fun bindReasonNetworkDataSourceImpl(impl: WeatherNetworkDataSourceImpl): IWeatherNetworkDataSource
}