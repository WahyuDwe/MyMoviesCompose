package com.dwi.mymovies

import android.app.Application
import com.dwi.mymovies.di.appModule
import com.dwi.mymovies.di.dataSourceModule
import com.dwi.mymovies.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyMovieApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // start koin
        startKoin {
            androidContext(this@MyMovieApp)
            modules(
                listOf(
                    networkModule,
                    dataSourceModule,
                    appModule
                )
            )
        }
    }
}