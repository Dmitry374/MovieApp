package com.example.movies.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composables.EmptyScreen
import com.example.composables.ErrorScreen
import com.example.composables.LoadingScreen
import com.example.movies.R
import com.example.movies.presentation.model.MovieUiItem
import com.example.movies.presentation.model.MoviesUiState
import com.example.movies.presentation.model.SearchUiItem

@Composable
fun MoviesRoute(
    onNavigateToDetails: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MoviesViewModel = hiltViewModel()
) {
    val uiState by viewModel.moviesStateFlow.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    MoviesScreen(
        uiState = uiState,
        searchQuery = searchQuery,
        onNavigateToDetails = onNavigateToDetails,
        onQueryChange = viewModel::onQueryChange,
        onClearQuery = viewModel::onClearQuery,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MoviesScreen(
    uiState: MoviesUiState,
    searchQuery: String,
    onNavigateToDetails: (Long) -> Unit,
    onQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {


        Spacer(modifier = Modifier.height(16.dp))
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            query = searchQuery,
            onQueryChange = onQueryChange,
            onSearch = onQueryChange,
            active = false,
            onActiveChange = {},
            placeholder = { Text(stringResource(R.string.search)) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                Icon(
                    modifier = Modifier.clickable {
                        onClearQuery()
                    },
                    imageVector = Icons.Default.Close,
                    contentDescription = null
                )
            }
        ) {

        }

        Spacer(modifier = Modifier.height(16.dp))

        MoviesContent(
            uiState = uiState,
            onNavigateToDetails = onNavigateToDetails,
            modifier = modifier
        )
    }
}

@Composable
private fun MoviesContent(
    uiState: MoviesUiState,
    onNavigateToDetails: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        MoviesUiState.Loading -> LoadingScreen(
            modifier = modifier
        )

        MoviesUiState.Empty -> EmptyScreen(
            modifier = modifier
        )

        is MoviesUiState.Movies -> {
            MoviesStateScreen(
                movies = uiState.movies,
                onNavigateToDetails = onNavigateToDetails
            )
        }

        is MoviesUiState.SearchResult -> {
            SearchResultStateScreen(
                searchItems = uiState.searchItems,
                onNavigateToDetails = onNavigateToDetails
            )
        }

        is MoviesUiState.Error -> ErrorScreen(
            onRetryAction = uiState.onRetryAction,
            modifier = modifier
        )
    }
}

@Composable
private fun MoviesStateScreen(
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

@Composable
private fun SearchResultStateScreen(
    searchItems: List<SearchUiItem>,
    onNavigateToDetails: (Long) -> Unit
) {
    LazyColumn(
        content = {
            items(searchItems) {
                HorizontalSearchItem(
                    name = it.name,
                    imageUrl = it.imageUrl,
                    year = it.year,
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
    val uiState = MoviesUiState.Movies(movies)
    MoviesScreen(
        uiState = uiState,
        searchQuery = "Avatar",
        onNavigateToDetails = {},
        onQueryChange = {},
        onClearQuery = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun MoviesScreenLoadingPreview() {
    MoviesScreen(
        uiState = MoviesUiState.Loading,
        searchQuery = "Avatar",
        onNavigateToDetails = {},
        onQueryChange = {},
        onClearQuery = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun MoviesScreenEmptyPreview() {
    MoviesScreen(
        uiState = MoviesUiState.Empty,
        searchQuery = "Avatar",
        onNavigateToDetails = {},
        onQueryChange = {},
        onClearQuery = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun MoviesScreenErrorPreview() {
    MoviesScreen(
        uiState = MoviesUiState.Error(
            onRetryAction = {}
        ),
        searchQuery = "Avatar",
        onNavigateToDetails = {},
        onQueryChange = {},
        onClearQuery = {}
    )
}
