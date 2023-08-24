package com.max.quotes.di

import com.max.quotes.network.ApiService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val QUOTES_URL = "https://api.quotable.io"


val networkModule = module {
    single { provideQuoteApi(provideRetrofit()) }
}

private fun provideRetrofit(): Retrofit {
    return Retrofit.Builder().baseUrl(QUOTES_URL).addConverterFactory(MoshiConverterFactory.create()).build()
}

private fun provideQuoteApi(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
