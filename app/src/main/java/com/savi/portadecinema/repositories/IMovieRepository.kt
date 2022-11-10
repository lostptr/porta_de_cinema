package com.savi.portadecinema.repositories

import com.savi.portadecinema.data.local.entities.MovieEntity
import com.savi.portadecinema.data.remote.tmdb.dto.MovieDetailsDto
import com.savi.portadecinema.data.remote.tmdb.dto.MoviePageDto
import com.savi.portadecinema.models.MovieDetails
import kotlinx.coroutines.flow.Flow
import retrofit2.Call

interface IMovieRepository {
    fun getPopular(page: Int): Call<MoviePageDto>
    fun getDetails(movieId: Int): Call<MovieDetailsDto>
    fun getFavorites(): Flow<List<MovieEntity>>
    suspend fun saveAsFavorite(movie: MovieDetails)
    suspend fun removeFavorite(movieId: Int)
    suspend fun checkIsFavorite(movieId: Int): Boolean
}