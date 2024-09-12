package com.example.model.domain

data class MovieDetail(
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
