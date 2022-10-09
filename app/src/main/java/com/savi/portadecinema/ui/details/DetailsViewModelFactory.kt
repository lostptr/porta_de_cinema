package com.savi.portadecinema.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.savi.portadecinema.repositories.MovieRepository

class DetailsViewModelFactory(private val movieRepository: MovieRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            DetailsViewModel(this.movieRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
