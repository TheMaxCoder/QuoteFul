package com.max.quotes.di

import android.content.Context
import androidx.room.Room
import com.max.quotes.data.QuoteRepository
import com.max.quotes.data.db.QuoteDao
import com.max.quotes.data.db.QuoteDb
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    singleOf(::provideDb)
    single { QuoteRepository(get(), get()) }
}

private fun provideDb(context: Context): QuoteDao {
    val db = Room.databaseBuilder(context, QuoteDb::class.java, "quotes").build()
    return db.quoteDao()
}
