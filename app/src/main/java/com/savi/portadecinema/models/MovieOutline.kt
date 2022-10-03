package com.savi.portadecinema.models

data class MovieOutline(
    val id: Int,
    val title: String,
    val overview: String,
    val rating: Float,
    val poster: String
)