package com.max.quotes.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.max.quotes.data.Repository
import com.max.quotes.data.db.Quote

class MainViewModel(private val repository: Repository) : ViewModel() {

    init {
//        viewModelScope.launch { repository.fetchNewQuotesAndSave() }
    }

    fun getQuotes(): LiveData<List<Quote>> {
        return repository.getQuotes()
    }
}
