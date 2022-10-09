package com.savi.portadecinema.models

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val rating: Float,
    val poster: String,
    val genres: List<String>,
    val isFavorite: Boolean,
)