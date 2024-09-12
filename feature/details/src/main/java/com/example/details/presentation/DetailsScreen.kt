package com.example.details.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.details.presentation.model.DetailsUiState
import com.example.composables.ErrorScreen

@Composable
fun DetailsRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.detailsStateFlow.collectAsStateWithLifecycle()
    DetailsScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onFavouriteClick = {},
        modifier = modifier
    )
}

@Composable
private fun DetailsScreen(
    uiState: DetailsUiState,
    onBackClick: () -> Unit,
    onFavouriteClick: (DetailsUiState.Success) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (uiState) {
            DetailsUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is DetailsUiState.Success -> {
                DetailsMovieContent(
                    title = uiState.title,
                    description = uiState.description,
                    posterUrl = uiState.posterUrl,
                    backdropUrl = uiState.backdropUrl,
                    genres = uiState.genres,
                    releaseDate = uiState.releaseDate,
                    rating = uiState.rating,
                    runtime = uiState.runtimeMinutes,
                    isFavorite = false,
                    onBackClick = onBackClick,
                    onFavouriteClick = { onFavouriteClick(uiState) }
                )
            }

            is DetailsUiState.Error -> ErrorScreen(
                onRetryAction = uiState.onRetryAction,
                modifier = modifier
            )
        }
    }
}
