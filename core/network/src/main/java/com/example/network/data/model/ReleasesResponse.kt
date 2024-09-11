package com.example.network.data.model

import com.example.model.domain.Movie
import com.example.network.data.mapper.NetworkResultMapper
import kotlinx.serialization.Serializable
import javax.inject.Inject

@Serializable
class ReleasesResponse(
    val releases: List<ReleaseRaw>
) {
    class ReleasesResponseMapper @Inject constructor(
        private val releaseRawMapper: ReleaseRaw.ReleaseRawMapper
    ) : NetworkResultMapper<ReleasesResponse, List<Movie>>() {
        override fun map(raw: ReleasesResponse): List<Movie> {
            return raw.releases.map(releaseRawMapper::map)
        }
    }
}
