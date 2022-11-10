package com.savi.portadecinema.data.remote.tmdb.dto

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.Date

data class MovieDetailsDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("genres")
    val genres: List<GenreDto>,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("vote_average")
    val voteAvg: Float,
    @SerializedName("release_date")
    val releaseDate: LocalDate,
    @SerializedName("runtime")
    val runtimeMin: Int
)