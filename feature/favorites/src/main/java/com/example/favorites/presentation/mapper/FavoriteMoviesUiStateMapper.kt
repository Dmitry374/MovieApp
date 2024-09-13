package com.example.favorites.presentation.mapper

import com.example.favorites.presentation.model.FavoriteMovieUiItem
import com.example.favorites.presentation.model.FavoriteMoviesUiState
import com.example.model.domain.FavoriteMovie
import javax.inject.Inject

class FavoriteMoviesUiStateMapper @Inject constructor() {

    fun map(favoriteMovies: List<FavoriteMovie>): FavoriteMoviesUiState {
        val uiItems = mapFavoriteMovies(favoriteMovies)
        return if (uiItems.isEmpty()) {
            FavoriteMoviesUiState.Empty
        } else {
            FavoriteMoviesUiState.Success(uiItems)
        }
    }

    private fun mapFavoriteMovies(favoriteMovies: List<FavoriteMovie>): List<FavoriteMovieUiItem> {
        return favoriteMovies.map(::mapFavoriteMovie)
    }

    private fun mapFavoriteMovie(favoriteMovie: FavoriteMovie): FavoriteMovieUiItem {
        return FavoriteMovieUiItem(
            id = favoriteMovie.id,
            title = favoriteMovie.title,
            poster = favoriteMovie.poster,
            releaseDate = favoriteMovie.releaseDate
        )
    }
}
