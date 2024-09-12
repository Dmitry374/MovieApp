package com.example.storage.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.storage.data.entities.FavoriteMoviesEntity

@Database(
    entities = [FavoriteMoviesEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    RoomConverters::class
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}
