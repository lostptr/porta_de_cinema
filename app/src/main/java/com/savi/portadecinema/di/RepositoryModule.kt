package com.savi.portadecinema.di

import com.savi.portadecinema.repositories.IMovieRepository
import com.savi.portadecinema.repositories.MovieRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<IMovieRepository> { MovieRepository(tmdbService = get(), dao = get()) }
}