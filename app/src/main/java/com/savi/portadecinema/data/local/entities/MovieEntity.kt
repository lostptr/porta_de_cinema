package com.savi.portadecinema.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "tmdb_id") val tmdbId: Int,
    val title: String?,
    val overview: String?,
    val rating: Float?,
    val poster: String?,
    val backdrop: String?,
    @ColumnInfo(name = "release_date") val releaseDate: String?,
    val duration: Int?,
    val genres: String? // comma separated values
)