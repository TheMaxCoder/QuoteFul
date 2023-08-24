package com.max.quotes.data

import com.max.quotes.data.db.QuoteDao
import com.max.quotes.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(val api: ApiService, val db: QuoteDao) {

    fun getQuotes() = db.getAllQuotes()

    suspend fun fetchNewQuotesAndSave() {
        withContext(Dispatchers.IO) {
            val quotesFromApi = api.getQuotesList()
            val quotes = quotesFromApi.map { it.toQuote() }
            db.insert(quotes)
        }
    }
}
