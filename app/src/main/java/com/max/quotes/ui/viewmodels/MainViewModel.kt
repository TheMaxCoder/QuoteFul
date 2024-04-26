package com.max.quotes.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.max.quotes.data.QuoteRepository
import com.max.quotes.data.db.Quote
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel(private val repository: QuoteRepository) : ViewModel() {

    private var dbQuotes: List<Quote> = emptyList()
    private val _uiState = MutableLiveData<UiState>().apply { UiState.Loading }
    val uiState: LiveData<UiState> = _uiState

    init {
        fetchQuotes()
    }

    fun fetchQuotes() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                repository.updateQuotesFromApi()
                dbQuotes = repository.getAllQuotesFromDb()
                handleFetchedQuotes(dbQuotes)
            } catch (e: Exception) {
                handleFetchError(e)
            }
        }
    }

    private fun handleFetchedQuotes(dbQuotes: List<Quote>) {
        if (dbQuotes.isNotEmpty()) {
            _uiState.value = UiState.Success(dbQuotes)
        } else {
            _uiState.value = UiState.Error("No Quotes Available. Please Reload.")
        }
    }

    private fun handleFetchError(e: Exception) {
        val errorMsg = if (e is IOException) {
            "Network Error: ${e.message}"
        } else {
            e.localizedMessage ?: "Unknown Error"
        }
        _uiState.value = UiState.Error(errorMsg)
    }

    fun updateFavorite(quote: Quote) {
        quote.isFavorite = quote.isFavorite.not()
        viewModelScope.launch { repository.updateQuote(quote) }
    }

    fun showFavoriteQuotes() {
        val favList = dbQuotes.filter { it.isFavorite }
        if (favList.isNotEmpty()) {
            _uiState.value = UiState.Success(favList)
        } else {
            _uiState.value = UiState.Error("No Favorites Yet.")
        }
    }

    fun showAllQuotes() {
        handleFetchedQuotes(dbQuotes)
    }
}
