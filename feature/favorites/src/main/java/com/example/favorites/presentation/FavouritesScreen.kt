package com.example.favorites.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FavoritesRoute(
    onNavigateToDetails: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    FavouritesScreen()
}

@Composable
private fun FavouritesScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Favourites")
    }
}
