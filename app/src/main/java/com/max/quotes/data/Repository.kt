package com.max.quotes.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.max.quotes.data.db.Quote
import com.max.quotes.data.db.QuoteDao
import com.max.quotes.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(val api: ApiService, val db: QuoteDao) {

    private val _quotes = MutableLiveData<List<Quote>>(emptyList())
    val quotes: LiveData<List<Quote>> = _quotes

    suspend fun getQuotes() {
        withContext(Dispatchers.IO) {
            _quotes.postValue(db.getAllQuotes())
            val quotesFromApi = api.getQuotesList()
            val quotes = quotesFromApi.map { it.toQuote() }
            db.insert(quotes)
        }
    }
}
