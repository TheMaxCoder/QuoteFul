package com.max.quotes.ui.viewmodels

import com.max.quotes.data.db.Quote

sealed class UiState {
    data class Success(val quotes: List<Quote>) : UiState()
    data object Loading : UiState()
    data class Error(val errorMsg: String) : UiState()
}
