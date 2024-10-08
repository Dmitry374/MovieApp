package com.example.model.domain

data class Movie(
    val id: Long,
    val title: String,
    val posterUrl: String,
    val seasonNumber: Int?,
    val sourceName: String,
    val releaseDate: String
)
