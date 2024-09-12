package com.example.network.data.model.detail

import com.example.model.domain.MovieDetail
import com.example.network.data.mapper.NetworkResultMapper
import kotlinx.serialization.Serializable
import javax.inject.Inject

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
) {
    class MovieDetailResponseMapper @Inject constructor() :
        NetworkResultMapper<MovieDetailResponse, MovieDetail>() {
        override fun map(raw: MovieDetailResponse): MovieDetail {
            return MovieDetail(
                id = raw.id,
                title = raw.title,
                poster = raw.poster,
                backdrop = raw.backdrop,
                releaseDate = raw.releaseDate,
                description = raw.plotOverview,
                rating = raw.userRating,
                genreNames = raw.genreNames,
                runtimeMinutes = raw.runtimeMinutes
            )
        }
    }
}
