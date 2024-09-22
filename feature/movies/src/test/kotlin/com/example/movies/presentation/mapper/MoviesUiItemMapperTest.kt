package com.example.movies.presentation.mapper

import com.example.model.domain.Movie
import com.example.movies.presentation.model.MovieUiItem
import com.example.movies.presentation.model.MoviesUiState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class MoviesUiItemMapperTest {

    private val mapper = MoviesUiItemMapper()

    @Test
    @DisplayName("Проверка маппинга списка фильмов")
    fun test_map() {
        /* Given */
        val expectedUiState = MoviesUiState.Movies(defaultMoviesUIItems)

        /* When */
        val result = mapper.map(defaultMovies)

        /* Then */
        assertEquals(expectedUiState, result)
    }

    @Test
    @DisplayName("Проверка маппинга пустого списка фильмов")
    fun test_map_emptyList() {
        /* Given */
        val movies = emptyList<Movie>()
        val expectedUiState = MoviesUiState.Empty

        /* When */
        val result = mapper.map(movies)

        /* Then */
        assertEquals(expectedUiState, result)
    }

    private companion object {
        val defaultMovies = listOf(
            Movie(
                id = 1,
                title = "Avatar",
                posterUrl = "posterUrl",
                seasonNumber = null,
                sourceName = "20th Century Fox",
                releaseDate = "2009-12-18"
            )
        )

        val defaultMoviesUIItems = listOf(
            MovieUiItem(
                id = 1,
                title = "Avatar",
                posterUrl = "posterUrl",
                seasonNumber = null,
                sourceName = "20th Century Fox",
                releaseDate = "18 December 2009"
            )
        )
    }
}
