package com.example.network.data.model.detail

import kotlinx.serialization.Serializable

@Serializable
class MovieDetailResponse(
    val id: Long,
    val title: String,
    val poster: String,
    val backdrop: String?,
    val genreNames: List<String>,
    val plotOverview: String,
    val releaseDate: String,
    val runtimeMinutes: Int?,
    val userRating: Double?
)
