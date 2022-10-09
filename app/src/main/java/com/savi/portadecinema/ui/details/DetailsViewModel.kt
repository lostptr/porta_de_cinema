package com.savi.portadecinema.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.savi.portadecinema.models.MovieDetails
import com.savi.portadecinema.models.MovieOutline
import com.savi.portadecinema.repositories.MovieRepository

class DetailsViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    fun getDetails(movieId: Int): LiveData<MovieDetails> {
        return Transformations.map(movieRepository.getDetails(movieId)) { details ->
            details?.let {
                MovieDetails(
                    it.id,
                    it.title,
                    it.overview,
                    it.voteAvg,
                    "https://image.tmdb.org/t/p/original/${it.backdropPath}",
                    it.genres.map { g -> g.name },
                    false
                )
            }
        }
    }
}