package com.example.details.data.mapper

import com.example.model.domain.MovieDetail
import com.example.storage.data.entities.FavoriteMoviesEntity
import javax.inject.Inject

class FavoriteMoviesEntityMapper @Inject constructor() {
    fun map(movieDetail: MovieDetail): FavoriteMoviesEntity {
        return FavoriteMoviesEntity(
            id = movieDetail.id,
            title = movieDetail.title,
            poster = movieDetail.poster,
            releaseDate = movieDetail.releaseDate
        )
    }
}
