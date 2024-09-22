package com.example.movies.presentation.mapper

import com.example.common.utils.convertDateToFormattedString
import com.example.model.domain.Movie
import com.example.movies.presentation.model.MovieUiItem
import com.example.movies.presentation.model.MoviesUiState
import javax.inject.Inject

class MoviesUiItemMapper @Inject constructor() {

    fun map(movies: List<Movie>): MoviesUiState {
        return if (movies.isNotEmpty()) {
            val uiItems = mapMovies(movies)
            MoviesUiState.Movies(uiItems)
        } else {
            MoviesUiState.Empty
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
            releaseDate = convertDateToFormattedString(movie.releaseDate)
        )
    }
}
