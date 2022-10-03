package com.savi.portadecinema.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.savi.portadecinema.services.tmdb.TmdbService
import com.savi.portadecinema.services.tmdb.models.MovieList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository(private val tmdbService: TmdbService) {
    fun getPopular(page: Int = 1): LiveData<MovieList?> {
        val liveData = MutableLiveData<MovieList?>()

        tmdbService.getPopularMovies(page).enqueue(object: Callback<MovieList> {
            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                Log.i("MovieRepository", "onResponse (pág. $page)")
                liveData.postValue(response.body())
            }

            override fun onFailure(call: Call<MovieList>, t: Throwable) {
                Log.e("MovieRepository", "onFailure: erro ao carregar lista de filmes populares (pág. $page)", t)
                liveData.postValue(null)
            }
        })

        return liveData
    }
}