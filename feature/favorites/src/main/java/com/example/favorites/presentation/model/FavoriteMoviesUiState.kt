package com.example.favorites.presentation.model

sealed interface FavoriteMoviesUiState {
    data object Loading : FavoriteMoviesUiState
    data object Empty : FavoriteMoviesUiState
    data class Success(val movies: List<FavoriteMovieUiItem>) : FavoriteMoviesUiState
    data class Error(val onRetryAction: () -> Unit) : FavoriteMoviesUiState
}
