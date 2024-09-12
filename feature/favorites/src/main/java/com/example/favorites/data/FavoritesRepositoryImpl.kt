package com.example.favorites.data

import com.example.favorites.data.mapper.FavoriteMovieMapper
import com.example.favorites.domain.FavoritesRepository
import com.example.model.domain.FavoriteMovie
import com.example.storage.data.db.MoviesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val moviesDao: MoviesDao,
    private val favoriteMovieMapper: FavoriteMovieMapper
) : FavoritesRepository {

    override fun listenFavoritesMovies(): Flow<List<FavoriteMovie>> {
        return moviesDao.listenFavoriteMovies()
            .map(favoriteMovieMapper::map)
    }
}
