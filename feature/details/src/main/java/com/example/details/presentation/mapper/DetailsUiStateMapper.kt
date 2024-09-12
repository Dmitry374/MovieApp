package com.example.details.presentation.mapper

import com.example.details.presentation.model.DetailsUiState
import com.example.model.domain.MovieDetail
import javax.inject.Inject

class DetailsUiStateMapper @Inject constructor() {

    fun map(detail: MovieDetail): DetailsUiState.Success {
        return DetailsUiState.Success(
            id = detail.id,
            title = detail.title,
            posterUrl = detail.poster,
            backdropUrl = detail.backdrop,
            releaseDate = detail.releaseDate,
            description = detail.description,
            rating = detail.rating,
            genres = detail.genreNames,
            runtimeMinutes = detail.runtimeMinutes,
            isFavorite = detail.isFavorite
        )
    }

    fun map(uiState: DetailsUiState.Success): MovieDetail {
        return MovieDetail(
            id = uiState.id,
            title = uiState.title,
            poster = uiState.posterUrl,
            backdrop = uiState.backdropUrl,
            releaseDate = uiState.releaseDate,
            description = uiState.description,
            rating = uiState.rating,
            genreNames = uiState.genres,
            runtimeMinutes = uiState.runtimeMinutes,
            isFavorite = uiState.isFavorite
        )
    }
}
