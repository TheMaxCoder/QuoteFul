package com.max.quotes.network

import retrofit2.http.GET

interface ApiService {

    @GET("/quotes/random/?limit=10")
    suspend fun getQuotesList(): List<ApiQuote>
}
