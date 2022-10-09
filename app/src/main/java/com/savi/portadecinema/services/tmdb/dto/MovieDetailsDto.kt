package com.savi.portadecinema.services.tmdb.dto

import com.google.gson.annotations.SerializedName

data class MovieDetailsDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("genres")
    val genres: List<GenreDto>,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("vote_average")
    val voteAvg: Float
)