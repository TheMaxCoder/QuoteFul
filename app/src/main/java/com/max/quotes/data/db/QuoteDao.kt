package com.max.quotes.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface QuoteDao {

    @Query("SELECT * FROM quote ORDER BY id DESC")
    suspend fun getAllQuotes(): List<Quote>

    @Query("DELETE FROM quote ")
    suspend fun nukeTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(quotes: List<Quote>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(quote: Quote)
}
