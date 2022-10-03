package com.savi.portadecinema.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import com.savi.portadecinema.models.Movie
import com.savi.portadecinema.repositories.MovieRepository

class HomeViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    fun getPopularMovies(page: Int): LiveData<List<Movie>> {
        return map(movieRepository.getPopular(page)) { movieList ->
            movieList?.results?.map {
                Movie(
                    it.id,
                    it.title,
                    it.overview,
                    it.voteAverage,
                    "https://image.tmdb.org/t/p/original/${it.posterPath}"
                )
            }
        }
    }
}