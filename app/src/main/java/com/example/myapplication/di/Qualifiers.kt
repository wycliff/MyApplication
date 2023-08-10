package com.example.myapplication.di


import dagger.Provides
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ProvideOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ProvideMapHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ProvideOkHttpClientAuth




