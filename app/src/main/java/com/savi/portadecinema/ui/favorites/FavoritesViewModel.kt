package com.savi.portadecinema.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savi.portadecinema.data.local.entities.MovieEntity
import com.savi.portadecinema.di.repositoryModule
import com.savi.portadecinema.repositories.IMovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(private val movieRepository: IMovieRepository) : ViewModel() {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    fun favoriteMovies(): Flow<List<MovieEntity>> =
        movieRepository.getFavorites()

}