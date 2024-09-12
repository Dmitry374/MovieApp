package com.example.details.data.mapper

import com.example.model.domain.MovieDetail
import com.example.network.data.model.detail.MovieDetailResponse
import javax.inject.Inject

class MovieDetailMapper @Inject constructor() {
    fun map(movieResponse: MovieDetailResponse, isFavorite: Boolean): MovieDetail {
        return MovieDetail(
            id = movieResponse.id,
            title = movieResponse.title,
            poster = movieResponse.poster,
            backdrop = movieResponse.backdrop,
            releaseDate = movieResponse.releaseDate,
            description = movieResponse.plotOverview,
            rating = movieResponse.userRating,
            genreNames = movieResponse.genreNames,
            runtimeMinutes = movieResponse.runtimeMinutes,
            isFavorite = isFavorite
        )
    }
}
