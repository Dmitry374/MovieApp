package com.example.network.data.model.release

import com.example.model.domain.Movie
import com.example.network.data.mapper.NetworkResultMapper
import kotlinx.serialization.Serializable
import javax.inject.Inject

@Serializable
class ReleaseRaw(
    val id: Long,
    val posterUrl: String,
    val seasonNumber: Int?,
    val sourceName: String,
    val sourceReleaseDate: String,
    val title: String
) {
    class ReleaseRawMapper @Inject constructor() : NetworkResultMapper<ReleaseRaw, Movie>() {
        override fun map(raw: ReleaseRaw): Movie {
            return Movie(
                id = raw.id,
                title = raw.title,
                posterUrl = raw.posterUrl,
                seasonNumber = raw.seasonNumber,
                sourceName = raw.sourceName,
                releaseDate = raw.sourceReleaseDate
            )
        }
    }
}
