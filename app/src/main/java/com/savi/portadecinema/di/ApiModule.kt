package com.savi.portadecinema.di

import com.savi.portadecinema.data.remote.tmdb.TmdbService
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    fun provideTmdbService(retrofit: Retrofit) : TmdbService =
        retrofit.create(TmdbService::class.java)

    single { provideTmdbService(retrofit = get()) }
}