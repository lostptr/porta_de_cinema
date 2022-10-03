package com.savi.portadecinema.services.tmdb

import com.savi.portadecinema.services.tmdb.models.MovieList
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbService {

    @GET("movie/popular?api_key=$apikey&language=$language")
    fun getPopularMovies(@Query("page") page: Int): Call<MovieList>

    companion object {
        const val apikey = "67175cb48e9814a9bc2b95df0baa4f84"
        const val language = "pt-BR"

        fun getInstance() = service

        private val service by lazy {
            val created = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            created.create(TmdbService::class.java)
        }
    }
}