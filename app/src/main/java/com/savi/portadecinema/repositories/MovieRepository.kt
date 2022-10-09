package com.savi.portadecinema.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.savi.portadecinema.services.tmdb.TmdbService
import com.savi.portadecinema.services.tmdb.dto.MovieDetailsDto
import com.savi.portadecinema.services.tmdb.dto.MoviePageDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository(private val tmdbService: TmdbService) {
    fun getPopular(page: Int = 1): LiveData<MoviePageDto?> {
        val liveData = MutableLiveData<MoviePageDto?>()

        tmdbService.getPopularMovies(page).enqueue(object : Callback<MoviePageDto> {
            override fun onResponse(call: Call<MoviePageDto>, response: Response<MoviePageDto>) {
                Log.i("MovieRepository", "onResponse (pág. $page)")
                liveData.postValue(response.body())
            }

            override fun onFailure(call: Call<MoviePageDto>, t: Throwable) {
                Log.e(
                    "MovieRepository",
                    "onFailure: erro ao carregar lista de filmes populares (pág. $page)",
                    t
                )
                liveData.postValue(null)
            }
        })

        return liveData
    }

    fun getDetails(movieId: Int): LiveData<MovieDetailsDto?> {
        val liveData = MutableLiveData<MovieDetailsDto?>()

        tmdbService.getMovieDetails(movieId).enqueue(object : Callback<MovieDetailsDto> {
            override fun onResponse(
                call: Call<MovieDetailsDto>,
                response: Response<MovieDetailsDto>
            ) {
                Log.i("MovieRepository", "getDetails (movie id: $movieId)")
                liveData.postValue(response.body())
            }

            override fun onFailure(call: Call<MovieDetailsDto>, t: Throwable) {
                Log.e(
                    "MovieRepository",
                    "onFailure: erro ao carregar os detalhes do filme (movie id: $movieId)",
                    t
                )
                liveData.postValue(null)
            }
        })

        return liveData
    }
}