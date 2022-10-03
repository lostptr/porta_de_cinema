package com.savi.portadecinema.models

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val rating: Float,
    val poster: String
)