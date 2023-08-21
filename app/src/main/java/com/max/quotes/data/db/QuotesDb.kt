package com.max.quotes.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Quote::class], version = 1)
abstract class QuotesDb : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao

}