package com.max.quotes

import android.app.Application
import com.max.quotes.di.dataModule
import com.max.quotes.di.networkModule
import com.max.quotes.di.uiModule
import logcat.AndroidLogcatLogger
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidLogcatLogger.installOnDebuggableApp(this)

        startKoin {
            androidContext(this@MainApp)
            modules(networkModule, dataModule, uiModule)
        }
    }
}
