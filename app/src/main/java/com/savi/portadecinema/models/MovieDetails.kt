package com.savi.portadecinema.models

import java.time.LocalDate

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val rating: Float,
    val poster: String,
    val backdrop: String,
    val genres: List<String>,
    val releaseDate: LocalDate,
    val duration: Int,
    val isFavorite: Boolean,
)