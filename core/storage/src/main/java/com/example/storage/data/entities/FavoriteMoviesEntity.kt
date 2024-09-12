package com.example.storage.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.storage.data.entities.FavoriteMoviesEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class FavoriteMoviesEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val poster: String,
    val releaseDate: String
) {
    companion object {
        const val TABLE_NAME = "FavoriteMovies"
    }
}
