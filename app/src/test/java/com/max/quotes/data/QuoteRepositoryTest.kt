package com.max.quotes.data

import com.max.quotes.data.db.QuoteDao
import com.max.quotes.data.mapping.toQuote
import com.max.quotes.network.ApiQuote
import com.max.quotes.network.ApiService
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.max.quotes.data.db.Quote
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
class QuoteRepositoryTest {

    private val api = mockk<ApiService>()
    private val dao = mockk<QuoteDao>()
    private lateinit var repository: QuoteRepository

    @Before
    fun setupMocks() {
        repository = QuoteRepository(api, dao)

    }

    @Test
    fun `updateQuotesFromApi should fetch quotes from API and save them in database`() = runBlocking {
        val dummyQuote = ApiQuote("id_1", "Be yourself.", "Ralph Waldo Emerson", "slug", 2, arrayOf("tags"))
        val apiQuotes = listOf(dummyQuote, dummyQuote.copy(_id = "id_2"))
        val quotes = apiQuotes.map { it.toQuote() }

        coEvery { api.getQuotesList() } returns apiQuotes
        coEvery { dao.insert(quotes) } just runs

        repository.updateQuotesFromApi()

        coVerify { dao.insert(quotes) }
    }

    @Test
    fun `getAllQuotesFromDb should fetch all quotes from the database`() = runBlocking {
        val dummyQuote1 = Quote("id_1", "Be yourself.", "Ralph Waldo Emerson")
        val dummyQuote2 = Quote("id_2", "Another quote.", "Author 2")
        val quotes = listOf(dummyQuote1, dummyQuote2)

        coEvery { dao.getAllQuotes() } returns quotes

        val fetchedQuotes = repository.getAllQuotesFromDb()

        assertEquals(2, fetchedQuotes.size)
        assertEquals(dummyQuote1, fetchedQuotes[0])
        assertEquals(dummyQuote2, fetchedQuotes[1])
    }
}