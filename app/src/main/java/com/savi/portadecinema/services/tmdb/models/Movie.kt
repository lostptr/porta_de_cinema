package com.savi.portadecinema.services.tmdb.models

import com.google.gson.annotations.SerializedName

//{
//    "adult": false,
//    "backdrop_path": "/5GA3vV1aWWHTSDO5eno8V5zDo8r.jpg",
//    "genre_ids": [
//    27,
//    53
//    ],
//    "id": 760161,
//    "original_language": "en",
//    "original_title": "Orphan: First Kill",
//    "overview": "Lena Klammer orquestra uma fuga brilhante de uma unidade psiquiátrica russa e viaja para os Estados Unidos, representando a filha desaparecida de uma família rica. Mas a nova vida de Lena como \"Esther\" vem com uma ruga inesperada e a coloca contra uma mãe que protegerá sua família a qualquer custo.",
//    "popularity": 8792.868,
//    "poster_path": "/uRdpVgEufzYNVnDPTXEjt0mpD99.jpg",
//    "release_date": "2022-07-27",
//    "title": "Órfã 2: A Origem",
//    "video": false,
//    "vote_average": 7,
//    "vote_count": 718
//}

data class Movie(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("vote_average")
    val voteAverage: Float
)