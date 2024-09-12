package com.example.storage.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.storage.data.entities.FavoriteMoviesEntity

@Database(
    entities = [FavoriteMoviesEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}
