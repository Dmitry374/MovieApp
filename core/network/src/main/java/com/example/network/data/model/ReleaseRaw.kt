package com.example.network.data.model

import com.example.model.domain.Movie
import com.example.network.data.mapper.NetworkResultMapper
import kotlinx.serialization.Serializable
import javax.inject.Inject

@Serializable
class ReleaseRaw(
    val id: Int,
    val imdbId: String,
    val isOriginal: Int,
    val posterUrl: String,
    val seasonNumber: Int?,
    val sourceId: Int,
    val sourceName: String,
    val sourceReleaseDate: String,
    val title: String,
    val tmdbId: Int,
    val tmdbType: String,
    val type: String
) {
    class ReleaseRawMapper @Inject constructor() : NetworkResultMapper<ReleaseRaw, Movie>() {
        override fun map(raw: ReleaseRaw): Movie {
            return Movie(
                id = raw.id,
                title = raw.title,
                posterUrl = raw.posterUrl,
                seasonNumber = raw.seasonNumber,
                sourceName = raw.sourceName,
                releaseDate = raw.sourceReleaseDate,
                type = mapType(raw.type)
            )
        }

        private fun mapType(type: String): Movie.Type {
            return when (type) {
                "movie" -> Movie.Type.MOVIE
                "tv_series" -> Movie.Type.TV_SERIES
                else -> Movie.Type.UNKNOWN
            }
        }
    }
}
