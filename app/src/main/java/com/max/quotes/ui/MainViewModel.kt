package com.max.quotes.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.max.quotes.data.Repository
import com.max.quotes.data.db.Quote
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    init {
//        viewModelScope.launch { repository.fetchNewQuotesAndSave() }
    }

    fun getQuotes(): LiveData<List<Quote>> {
        return repository.getQuotes()
    }
}
