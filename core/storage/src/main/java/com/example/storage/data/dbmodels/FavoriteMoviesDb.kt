package com.example.storage.data.dbmodels

class FavoriteMoviesDb(
    val id: Long,
    val title: String,
    val poster: String,
    val backdrop: String?,
    val releaseDate: String,
    val description: String,
    val rating: Double?,
    val genreNames: List<String>,
    val runtimeMinutes: Int?
)
