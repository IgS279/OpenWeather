package com.igld279.openweather

import android.app.Application
import android.util.Log
import com.igld279.openweather.di.databaseModule
import com.igld279.openweather.di.repoModule
import com.igld279.openweather.di.viewModelModule
import com.igld279.openweather.ui.TAG
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "App onCreate +")

        startKoin{
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(
                listOf(viewModelModule, repoModule, databaseModule)
            )
        }
    }

    companion object {
        var AppId = "6389d9ae480fe3dd063977c9f9e9cd3c"
        var units = "metric"
        val lang = "ru"
    }
}