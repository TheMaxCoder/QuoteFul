package com.max.quotes.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.max.quotes.data.Repository
import com.max.quotes.data.db.Quote
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    fun getQuotes(): LiveData<List<Quote>> {
        viewModelScope.launch { repository.getQuotes() }
        return repository.quotes
    }
}