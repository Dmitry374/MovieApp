package com.example.favorites.presentation

import app.cash.turbine.test
import com.example.favorites.domain.FavoritesRepository
import com.example.favorites.presentation.mapper.FavoriteMoviesUiStateMapper
import com.example.favorites.presentation.model.FavoriteMovieUiItem
import com.example.favorites.presentation.model.FavoriteMoviesUiState
import com.example.model.domain.FavoriteMovie
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.Extensions

@OptIn(ExperimentalCoroutinesApi::class)
@Extensions(ExtendWith(MockKExtension::class))
class FavoritesViewModelTest(
    @MockK private val repository: FavoritesRepository,
    @MockK private val favoriteMoviesUiStateMapper: FavoriteMoviesUiStateMapper
) {
    @BeforeEach
    fun beforeEach() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @AfterEach
    fun afterEach() {
        Dispatchers.resetMain()
    }

    @Test
    @DisplayName("Проверка успешного получения избранных фильмов")
    fun test_listenFavoriteMovies_success() = runTest {
        /* Given */
        every {
            repository.listenFavoritesMovies()
        } returns flowOf(defaultFavoriteMovies)

        every {
            favoriteMoviesUiStateMapper.map(defaultFavoriteMovies)
        } returns favoriteMoviesUiState

        /* When */
        val viewModel = FavoritesViewModel(
            repository = repository,
            favoriteMoviesUiStateMapper = favoriteMoviesUiStateMapper
        )

        /* Then */
        viewModel.favoriteMoviesStateFlow.test {
            assertEquals(favoriteMoviesUiState, awaitItem())
        }

        verifySequence {
            repository.listenFavoritesMovies()
            favoriteMoviesUiStateMapper.map(defaultFavoriteMovies)
        }
    }

    @Test
    @DisplayName("Проверка ошибки при получении избранных фильмов")
    fun test_listenFavoriteMovies_error() = runTest {
        /* Given */
        val testException = Throwable("Test exception")

        every {
            repository.listenFavoritesMovies()
        } returns flow {
            throw testException
        }

        /* When */
        val viewModel = FavoritesViewModel(
            repository = repository,
            favoriteMoviesUiStateMapper = favoriteMoviesUiStateMapper
        )

        /* Then */
        viewModel.favoriteMoviesStateFlow.test {
            assertEquals(FavoriteMoviesUiState.Error::class, awaitItem()::class)
        }

        verifySequence {
            repository.listenFavoritesMovies()
        }
    }

    private companion object {
        val defaultFavoriteMovies = listOf(
            FavoriteMovie(
                id = 1,
                title = "Avatar",
                poster = "poster",
                releaseDate = "2009-12-18"
            )
        )

        val defaultFavoriteUiItems = listOf(
            FavoriteMovieUiItem(
                id = 1,
                title = "Avatar",
                poster = "poster",
                releaseDate = "2009-12-18"
            )
        )

        val favoriteMoviesUiState = FavoriteMoviesUiState.Success(defaultFavoriteUiItems)
    }
}
