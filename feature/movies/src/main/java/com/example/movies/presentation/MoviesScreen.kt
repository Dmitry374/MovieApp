package com.example.movies.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composables.EmptyScreen
import com.example.composables.ErrorScreen
import com.example.movies.presentation.model.MovieUiItem
import com.example.movies.presentation.model.MoviesUiState

@Composable
fun MoviesRoute(
    onNavigateToDetails: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MoviesViewModel = hiltViewModel()
) {
    val uiState by viewModel.moviesStateFlow.collectAsStateWithLifecycle()
    MoviesScreen(
        uiState = uiState,
        onNavigateToDetails = onNavigateToDetails,
        modifier = modifier
    )
}

@Composable
private fun MoviesScreen(
    uiState: MoviesUiState,
    onNavigateToDetails: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (uiState) {
            MoviesUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            MoviesUiState.Empty -> EmptyScreen(
                modifier = modifier
            )

            is MoviesUiState.Success -> {
                SuccessStateScreen(
                    movies = uiState.movies,
                    onNavigateToDetails = onNavigateToDetails
                )
            }

            is MoviesUiState.Error -> ErrorScreen(
                onRetryAction = uiState.onRetryAction,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun SuccessStateScreen(
    movies: List<MovieUiItem>,
    onNavigateToDetails: (Long) -> Unit
) {
    LazyColumn(
        content = {
            items(movies) {
                HorizontalMovieItem(
                    title = it.title,
                    imageUrl = it.posterUrl,
                    seasonNumber = it.seasonNumber,
                    sourceName = it.sourceName,
                    releaseDate = it.releaseDate,
                    onClick = { onNavigateToDetails(it.id) }
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun MoviesScreenSuccessPreview() {
    val movies = listOf(
        MovieUiItem(
            id = 1,
            title = "Ant-Man and the Wasp: Quantumania",
            posterUrl = "https://image.tmdb.org/t/p/original/lKHy0ntGPdQeFwvNq8gK1D0anEr.jpg",
            seasonNumber = null,
            sourceName = "Disney+",
            releaseDate = "2022-02-17"
        ),
        MovieUiItem(
            id = 2,
            title = "Lost",
            posterUrl = "https://image.tmdb.org/t/p/original/lKHy0ntGPdQeFwvNq8gK1D0anEr.jpg",
            seasonNumber = 2,
            sourceName = "Hulu",
            releaseDate = "2005-05-11"
        )
    )
    val uiState = MoviesUiState.Success(movies)
    MoviesScreen(
        uiState = uiState,
        onNavigateToDetails = {}
    )
}
