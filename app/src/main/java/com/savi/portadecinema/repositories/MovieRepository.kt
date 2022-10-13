package com.savi.portadecinema.repositories

import com.savi.portadecinema.services.tmdb.TmdbService
import com.savi.portadecinema.services.tmdb.dto.MovieDetailsDto
import com.savi.portadecinema.services.tmdb.dto.MoviePageDto
import retrofit2.Call

class MovieRepository(private val tmdbService: TmdbService) {
    fun getPopular(page: Int = 1): Call<MoviePageDto>
        = tmdbService.getPopularMovies(page)

    fun getDetails(movieId: Int): Call<MovieDetailsDto>
        = tmdbService.getMovieDetails(movieId)
}