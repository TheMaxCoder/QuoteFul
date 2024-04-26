package com.max.quotes.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.max.quotes.data.QuoteRepository
import com.max.quotes.data.db.Quote
import com.max.quotes.data.db.QuoteDao
import com.max.quotes.network.ApiQuote
import com.max.quotes.network.ApiService
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mainViewModel: MainViewModel
    private lateinit var repository: QuoteRepository
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        mainViewModel = MainViewModel(repository)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun testQuotesFetchedSuccessfully() = testDispatcher.runBlockingTest {
        // Mock behavior for successful quotes retrieval
        val dummyQuote = Quote("id_1", "Be yourself.", "Ralph")
        val quotes = listOf(dummyQuote)
        coEvery { repository.updateQuotesFromApi() } just runs
        coEvery { repository.getAllQuotesFromDb() } returns quotes

        // Observe changes in uiState
        val observer = mockk<Observer<UiState>>(relaxed = true)
        mainViewModel.uiState.observeForever(observer)

        // Trigger fetching quotes
        mainViewModel.fetchQuotes()

        // Check if loading state was emitted first and then success state with quotes
        verifyOrder {
            observer.onChanged(UiState.Loading)
            observer.onChanged(UiState.Success(quotes))
        }
    }

    @Test
    fun testNoQuotesAvailable() = testDispatcher.runBlockingTest {
        // Mock behavior for empty quotes retrieval
        coEvery { repository.updateQuotesFromApi() } just runs
        coEvery { repository.getAllQuotesFromDb() } returns emptyList()

        // Observe changes in uiState
        val observer = mockk<Observer<UiState>>(relaxed = true)
        mainViewModel.uiState.observeForever(observer)

        // Trigger fetching quotes
        mainViewModel.fetchQuotes()

        // Check if loading state was emitted first and then error state due to no quotes
        verifyOrder {
            observer.onChanged(UiState.Loading)
            observer.onChanged(UiState.Error("No Quotes Available. Please Reload."))
        }
    }

    @Test
    fun testExceptionDuringFetchingQuotes() = testDispatcher.runBlockingTest {
        // Mock behavior for throwing an exception during quotes retrieval
        val exception = IOException("Network error")
        coEvery { repository.updateQuotesFromApi() } throws exception

        // Observe changes in uiState
        val observer = mockk<Observer<UiState>>(relaxed = true)
        mainViewModel.uiState.observeForever(observer)

        // Trigger fetching quotes
        mainViewModel.fetchQuotes()

        // Check if loading state was emitted first and then error state due to exception
        verifyOrder {
            observer.onChanged(UiState.Loading)
            observer.onChanged(UiState.Error("Network Error: ${exception.message}"))
        }
    }
}
