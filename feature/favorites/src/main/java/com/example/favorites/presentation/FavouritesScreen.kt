package com.example.favorites.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composables.EmptyScreen
import com.example.composables.ErrorScreen
import com.example.favorites.presentation.model.FavoriteMovieUiItem
import com.example.favorites.presentation.model.FavoriteMoviesUiState

@Composable
fun FavoritesRoute(
    onNavigateToDetails: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val uiState by viewModel.favoriteMoviesStateFlow.collectAsStateWithLifecycle()
    FavouritesScreen(
        uiState = uiState,
        onNavigateToDetails = onNavigateToDetails,
        modifier = modifier
    )
}

@Composable
private fun FavouritesScreen(
    uiState: FavoriteMoviesUiState,
    onNavigateToDetails: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (uiState) {
            FavoriteMoviesUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            FavoriteMoviesUiState.Empty -> EmptyScreen(modifier)

            is FavoriteMoviesUiState.Success -> {
                SuccessStateScreen(
                    favoriteMovies = uiState.movies,
                    onNavigateToDetails = onNavigateToDetails
                )
            }

            is FavoriteMoviesUiState.Error -> ErrorScreen(
                onRetryAction = uiState.onRetryAction,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun SuccessStateScreen(
    favoriteMovies: List<FavoriteMovieUiItem>,
    onNavigateToDetails: (Long) -> Unit
) {
    LazyVerticalStaggeredGrid(
        modifier = Modifier.fillMaxSize(),
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 0.dp,
        horizontalArrangement = Arrangement.Center,
        content = {
            items(favoriteMovies) {
                VerticalMovieItem(
                    title = it.title,
                    release = it.releaseDate,
                    posterUrl = it.poster,
                    onClick = { onNavigateToDetails(it.id) }
                )

                if (it == favoriteMovies.last()) {
                    Spacer(modifier = Modifier.height(80.dp))
                }

            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun FavoriteMoviesScreenSuccessPreview() {
    val favoriteMovies = listOf(
        FavoriteMovieUiItem(
            id = 1,
            title = "Ant-Man and the Wasp: Quantumania",
            poster = "https://image.tmdb.org/t/p/original/lKHy0ntGPdQeFwvNq8gK1D0anEr.jpg",
            releaseDate = "2022-02-17"
        ),
        FavoriteMovieUiItem(
            id = 2,
            title = "Lost",
            poster = "https://image.tmdb.org/t/p/original/lKHy0ntGPdQeFwvNq8gK1D0anEr.jpg",
            releaseDate = "2005-05-11"
        ),
        FavoriteMovieUiItem(
            id = 3,
            title = "Spider Man",
            poster = "https://image.tmdb.org/t/p/w500/6KErczPBROQty7QoIsaa6wJYXZi.jpg",
            releaseDate = "2002-05-03"
        )
    )
    val uiState = FavoriteMoviesUiState.Success(favoriteMovies)
    FavouritesScreen(
        uiState = uiState,
        onNavigateToDetails = {}
    )
}
