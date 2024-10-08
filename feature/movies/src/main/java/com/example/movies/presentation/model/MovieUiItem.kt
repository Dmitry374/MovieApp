package com.example.movies.presentation.model

data class MovieUiItem(
    val id: Long,
    val title: String,
    val posterUrl: String,
    val seasonNumber: Int?,
    val sourceName: String,
    val releaseDate: String
)
