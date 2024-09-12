package com.example.favorites.domain

import com.example.model.domain.FavoriteMovie
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun listenFavoritesMovies(): Flow<List<FavoriteMovie>>
}
