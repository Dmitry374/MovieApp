package com.example.movies.presentation.model

sealed interface MoviesUiState {
    data object Loading : MoviesUiState
    data object Empty : MoviesUiState
    data class Success(val movies: List<MovieUiItem>) : MoviesUiState
    data class Error(val onRetryAction: () -> Unit) : MoviesUiState
}
