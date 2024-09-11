package com.example.movies.presentation.mapper

import com.example.model.domain.Movie
import com.example.movies.presentation.model.MovieUiItem
import com.example.movies.presentation.model.MoviesUiState
import javax.inject.Inject

class MoviesUiStateMapper @Inject constructor() {

    fun map(movies: List<Movie>): MoviesUiState {
        val uiItems = mapMovies(movies)
        return if (uiItems.isEmpty()) {
            MoviesUiState.Empty
        } else {
            MoviesUiState.Success(uiItems)
        }
    }

    private fun mapMovies(movies: List<Movie>): List<MovieUiItem> = movies.map(::mapMovie)

    private fun mapMovie(movie: Movie): MovieUiItem {
        return MovieUiItem(
            id = movie.id,
            title = movie.title,
            posterUrl = movie.posterUrl,
            seasonNumber = movie.seasonNumber,
            sourceName = movie.sourceName,
            releaseDate = movie.releaseDate
        )
    }
}
