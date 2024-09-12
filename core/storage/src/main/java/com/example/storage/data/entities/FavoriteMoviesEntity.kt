package com.example.storage.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.storage.data.entities.FavoriteMoviesEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class FavoriteMoviesEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val poster: String,
    val backdrop: String?,
    val releaseDate: String,
    val description: String,
    val rating: Double?,
    val genreNames: List<String>,
    val runtimeMinutes: Int?
) {
    companion object {
        const val TABLE_NAME = "FavoriteMovies"
    }
}
