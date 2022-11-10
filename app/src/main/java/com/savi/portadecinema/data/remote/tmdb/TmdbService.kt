package com.savi.portadecinema.data.remote.tmdb

import com.google.gson.*
import com.savi.portadecinema.data.remote.tmdb.dto.MovieDetailsDto
import com.savi.portadecinema.data.remote.tmdb.dto.MoviePageDto
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
                .addConverterFactory(GsonConverterFactory.create(getConverter()))
                .build()

            created.create(TmdbService::class.java)
        }

        private fun getConverter() = GsonBuilder()
            .registerTypeAdapter(LocalDate::class.java, object : JsonDeserializer<LocalDate> {
                override fun deserialize(
                    json: JsonElement?,
                    typeOfT: Type?,
                    context: JsonDeserializationContext?
                ): LocalDate {
                    return LocalDate.parse(
                        json?.asString,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    )
                }
            })
            .create()


        fun getInstance(): TmdbService = service

        fun getImageFullPath(relativePath: String, size: String = "original") =
            "$imageBasePath$size/$relativePath"

    }
}