package com.example.network.data.model

import kotlinx.serialization.Serializable

@Serializable
class ReleaseRaw(
    val id: Int,
    val imdbId: String,
    val isOriginal: Int,
    val posterUrl: String,
    val seasonNumber: Int,
    val sourceId: Int,
    val sourceName: String,
    val sourceReleaseDate: String,
    val title: String,
    val tmdbId: Int,
    val tmdbType: String,
    val type: String
)
