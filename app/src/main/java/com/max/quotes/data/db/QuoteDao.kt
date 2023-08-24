package com.max.quotes.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuoteDao {

    @Query("SELECT * FROM quote")
    fun getAllQuotes(): LiveData<List<Quote>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(quotes: List<Quote>)

    @Query("DELETE FROM quote ")
    fun nukeTable()
}
