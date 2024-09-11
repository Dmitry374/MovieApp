package com.example.movies.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel = hiltViewModel()
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Movies")
    }
}
