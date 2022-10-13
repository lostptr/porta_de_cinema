package com.savi.portadecinema.services.tmdb

import com.savi.portadecinema.services.tmdb.dto.MovieDetailsDto
import com.savi.portadecinema.services.tmdb.dto.MoviePageDto
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbService {

    @GET("movie/popular?api_key=$apikey&language=$language")
    fun getPopularMovies(@Query("page") page: Int): Call<MoviePageDto>

    @GET("movie/{id}?api_key=$apikey&language=$language")
    fun getMovieDetails(@Path("id") id: Int): Call<MovieDetailsDto>

    companion object {
        private const val apikey = "67175cb48e9814a9bc2b95df0baa4f84"
        private const val language = "pt-BR"
        private const val imageBasePath = "https://image.tmdb.org/t/p/"

        private val service by lazy {
            val created = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            created.create(TmdbService::class.java)
        }

        fun getInstance() = service

        fun getImageFullPath(relativePath: String, size: String = "original") =
           "$imageBasePath$size/$relativePath"

    }
}