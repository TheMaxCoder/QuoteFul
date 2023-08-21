package com.max.quotes

import android.app.Application
import com.max.quotes.di.dataModule
import com.max.quotes.di.networkModule
import com.max.quotes.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        startKoin {
            androidContext(this@MainApp)
            modules(networkModule, dataModule, uiModule)
        }
    }
}