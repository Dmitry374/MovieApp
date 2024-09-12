package com.example.favorites.data.mapper

import com.example.model.domain.FavoriteMovie
import com.example.storage.data.dbmodels.FavoriteMoviesDb
import javax.inject.Inject

class FavoriteMovieMapper @Inject constructor() {

    fun map(favoriteMoviesDbs: List<FavoriteMoviesDb>): List<FavoriteMovie> {
        return favoriteMoviesDbs.map(::mapFavoriteMovie)
    }

    private fun mapFavoriteMovie(favoriteMoviesDb: FavoriteMoviesDb): FavoriteMovie {
        return FavoriteMovie(
            id = favoriteMoviesDb.id,
            title = favoriteMoviesDb.title,
            poster = favoriteMoviesDb.poster,
            releaseDate = favoriteMoviesDb.releaseDate
        )
    }
}
