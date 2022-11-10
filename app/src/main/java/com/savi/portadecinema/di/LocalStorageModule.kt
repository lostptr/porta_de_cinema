package com.savi.portadecinema.di

import android.content.Context
import androidx.room.Room
import com.savi.portadecinema.data.local.LocalDatabase
import com.savi.portadecinema.data.local.dao.MovieDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localStorageModule = module {
    fun provideLocalDatabase(context: Context): LocalDatabase =
        Room.databaseBuilder(context, LocalDatabase::class.java, "moviesdb")
            .fallbackToDestructiveMigration()
            .build()

    fun provideMovieDao(localDatabase: LocalDatabase): MovieDao =
        localDatabase.movies()

    single { provideLocalDatabase(androidContext()) }

    // DAO
    single { provideMovieDao(localDatabase = get()) }
}