package com.savi.portadecinema.services.tmdb.dto

import com.google.gson.annotations.SerializedName

data class MoviePageDto(
    @SerializedName("page")
    val page: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("results")
    val results: List<MovieDto>
)