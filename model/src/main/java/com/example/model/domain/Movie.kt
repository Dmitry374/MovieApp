package com.example.model.domain

data class Movie(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val seasonNumber: Int?,
    val sourceName: String,
    val releaseDate: String,
    val type: Type
) {
    enum class Type {
        MOVIE, TV_SERIES, UNKNOWN
    }
}
