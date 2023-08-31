package com.max.quotes.data

import com.max.quotes.data.db.Quote
import com.max.quotes.data.db.QuoteDao
import com.max.quotes.network.ApiQuote
import com.max.quotes.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(val api: ApiService, val db: QuoteDao) {

    fun getQuotes() = db.getAllQuotes()

    suspend fun fetchNewQuotesAndSave() {
        val newQuotes = fetchFromApi()
        val quotes = newQuotes.map { it.toQuote() }
        db.insert(quotes)
    }

    suspend fun fetchFromApi(): List<ApiQuote> {
        return withContext(Dispatchers.IO) {
            val quotesFromApi = api.getQuotesList()
            quotesFromApi
        }
    }
}
