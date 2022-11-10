package com.savi.portadecinema.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.savi.portadecinema.data.local.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getAll(): Flow<List<MovieEntity>>

    @Insert
    suspend fun insert(movie: MovieEntity)

    @Query("DELETE FROM movie WHERE tmdb_id = :tmdbId")
    suspend fun delete(tmdbId: Int)

    @Query("SELECT EXISTS(SELECT id FROM movie WHERE tmdb_id = :tmdbId)")
    suspend fun checkIsFavorite(tmdbId: Int): Boolean
}