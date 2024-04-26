package com.max.quotes.data

import com.max.quotes.data.db.Quote
import com.max.quotes.data.db.QuoteDao
import com.max.quotes.data.mapping.toQuote
import com.max.quotes.network.ApiQuote
import com.max.quotes.network.ApiService

class QuoteRepository(private val api: ApiService, private val db: QuoteDao) {

    suspend fun updateQuotesFromApi() {
        val newQuotes = fetchNewQuotesFromApi()
        val quotes = newQuotes.map { it.toQuote() }
        insertQuotes(quotes)
    }

    private suspend fun fetchNewQuotesFromApi(): List<ApiQuote> {
        return api.getQuotesList()
    }

    private suspend fun insertQuotes(quotes: List<Quote>) {
        db.insert(quotes)
    }

    suspend fun getAllQuotesFromDb(): List<Quote> {
        return db.getAllQuotes()
    }

    suspend fun updateQuote(quote: Quote) {
        db.update(quote)
    }
}
