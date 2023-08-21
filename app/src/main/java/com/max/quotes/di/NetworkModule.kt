package com.max.quotes.di

import com.max.quotes.network.ApiService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    singleOf(::provideRetrofit)
    singleOf(::provideApi)
}

private fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://api.quotable.io")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
}

private fun provideApi(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)