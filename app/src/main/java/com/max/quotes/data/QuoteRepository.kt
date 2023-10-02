package com.max.quotes.data

import androidx.lifecycle.LiveData
import com.max.quotes.data.db.Quote
import com.max.quotes.data.db.QuoteDao
import com.max.quotes.network.ApiQuote
import com.max.quotes.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import logcat.logcat

class QuoteRepository(private val api: ApiService, private val db: QuoteDao) {

    fun getQuotes(): LiveData<List<Quote>> {
        return db.getAllQuotes()
    }

    suspend fun fetchNewQuotesAndSave() {
        try {
            val newQuotes = fetchFromApi()
            val quotes = newQuotes.map { it.toQuote() }
            db.insert(quotes)
        } catch (e: Exception) {
            logcat { e.toString() }
        }
    }

    private suspend fun fetchFromApi(): List<ApiQuote> {
        return withContext(Dispatchers.IO) {
            api.getQuotesList()
        }
    }
}