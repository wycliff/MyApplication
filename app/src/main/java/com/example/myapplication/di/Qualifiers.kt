package com.example.myapplication.di


import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ProvideOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ProvideMapHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ProvideOkHttpClientAuth




