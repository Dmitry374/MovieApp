package com.example.details.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composables.ErrorScreen
import com.example.details.presentation.model.DetailsUiState

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
        onFavouriteClick = viewModel::onFavoriteClick,
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
    Box(modifier = modifier
        .fillMaxSize()
        .testTag("details_screen")) {
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
                    isFavorite = uiState.isFavorite,
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
