package com.max.quotes.di

import android.content.Context
import androidx.room.Room
import com.max.quotes.data.Repository
import com.max.quotes.data.db.QuoteDao
import com.max.quotes.data.db.QuotesDb
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import timber.log.Timber

val dataModule = module {
    singleOf(::provideDb)
    single { Repository(get(), get()) }
}

private fun provideDb(context: Context): QuoteDao {
    val db = Room.databaseBuilder(context, QuotesDb::class.java, "quotes").build()
    return db.quoteDao()
}
