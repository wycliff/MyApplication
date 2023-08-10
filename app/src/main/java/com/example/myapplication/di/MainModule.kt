package com.example.myapplication.di

import com.example.myapplication.model.dataSource.network.abstraction.IWeatherNetworkDataSource
import com.example.myapplication.model.dataSource.network.apiService.WeatherApiInterface
import com.example.myapplication.model.dataSource.network.implementation.WeatherNetworkDataSourceImpl
import com.example.myapplication.model.repository.abstraction.IWeatherRepository
import com.example.myapplication.model.repository.implementation.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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

    @Singleton
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class MainBindingModule {
    @Binds
    abstract fun bindWeatherNetworkDataSourceImpl(impl: WeatherNetworkDataSourceImpl): IWeatherNetworkDataSource

    @Binds
    abstract fun bindWeatherNetworkRepositorySourceImpl(impl: WeatherRepositoryImpl): IWeatherRepository
}