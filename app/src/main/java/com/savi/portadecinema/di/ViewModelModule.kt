package com.savi.portadecinema.di

import com.savi.portadecinema.ui.details.DetailsViewModel
import com.savi.portadecinema.ui.favorites.FavoritesViewModel
import com.savi.portadecinema.ui.popular.PopularViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { PopularViewModel(movieRepository = get()) }
    viewModel { DetailsViewModel(movieRepository = get()) }
    viewModel { FavoritesViewModel(movieRepository = get()) }
}