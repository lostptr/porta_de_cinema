package com.savi.portadecinema

import android.app.Application
import androidx.room.Room
import com.savi.portadecinema.data.local.LocalDatabase
import com.savi.portadecinema.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(this@MainApplication)
            modules(
                listOf(
                    networkModule,
                    apiModule,
                    localStorageModule,
                    repositoryModule,
                    viewModelModule,
                )
            )
        }
    }
}