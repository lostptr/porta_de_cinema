package com.savi.portadecinema.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.savi.portadecinema.data.local.dao.MovieDao
import com.savi.portadecinema.data.local.entities.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun movies(): MovieDao
}