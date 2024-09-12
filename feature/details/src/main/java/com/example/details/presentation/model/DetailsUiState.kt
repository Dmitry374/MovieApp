package com.example.details.presentation.model

sealed interface DetailsUiState {

    data object Loading : DetailsUiState

    data class Success(
        val id: Long,
        val title: String,
        val posterUrl: String,
        val backdropUrl: String?,
        val releaseDate: String,
        val description: String,
        val rating: Double?,
        val genres: List<String>,
        val runtimeMinutes: Int?,
        val isFavorite: Boolean
    ) : DetailsUiState

    data class Error(val onRetryAction: () -> Unit) : DetailsUiState
}
