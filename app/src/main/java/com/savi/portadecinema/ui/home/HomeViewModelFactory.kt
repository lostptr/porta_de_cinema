package com.savi.portadecinema.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.savi.portadecinema.repositories.MovieRepository

class HomeViewModelFactory(private val movieRepository: MovieRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(this.movieRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}