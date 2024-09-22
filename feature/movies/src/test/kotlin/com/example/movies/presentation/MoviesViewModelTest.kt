package com.example.movies.presentation

import app.cash.turbine.test
import com.example.common.domain.NetworkResult
import com.example.model.domain.Movie
import com.example.model.domain.SearchItem
import com.example.movies.domain.MoviesRepository
import com.example.movies.presentation.mapper.MoviesUiItemMapper
import com.example.movies.presentation.mapper.SearchUiItemMapper
import com.example.movies.presentation.model.MovieUiItem
import com.example.movies.presentation.model.MoviesUiState
import com.example.movies.presentation.model.SearchUiItem
import io.mockk.coEvery
import io.mockk.coExcludeRecords
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
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
class MoviesViewModelTest(
    @MockK private val repository: MoviesRepository,
    @MockK private val moviesUiItemMapper: MoviesUiItemMapper,
    @MockK private val searchUiItemMapper: SearchUiItemMapper
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
    @DisplayName("Проверка успешного получения фильмов")
    fun test_getMovies_success() = runTest {
        /* Given */
        coEvery {
            repository.getMovies()
        } returns NetworkResult.Success(defaultMovies)

        every {
            moviesUiItemMapper.map(defaultMovies)
        } returns moviesUiState

        /* When */
        val viewModel = MoviesViewModel(
            repository = repository,
            moviesUiItemMapper = moviesUiItemMapper,
            searchUiItemMapper = searchUiItemMapper
        )

        /* Then */
        viewModel.moviesStateFlow.test {
            assertEquals(moviesUiState, awaitItem())
        }

        coVerifySequence {
            repository.getMovies()
            moviesUiItemMapper.map(defaultMovies)
        }
    }

    @Test
    @DisplayName("Проверка ошибки при получении фильмов")
    fun test_getMovies_error() = runTest {
        /* Given */
        coEvery {
            repository.getMovies()
        } returns NetworkResult.Error(Throwable("Test exception"))

        /* When */
        val viewModel = MoviesViewModel(
            repository = repository,
            moviesUiItemMapper = moviesUiItemMapper,
            searchUiItemMapper = searchUiItemMapper
        )

        /* Then */
        viewModel.moviesStateFlow.test {
            assertEquals(MoviesUiState.Error::class, awaitItem()::class)
        }

        coVerifySequence {
            repository.getMovies()
        }
    }

    @Test
    @DisplayName("Проверка успешного поиска фильмов")
    fun test_onQueryChange_success() = runTest {
        /* Given */
        val query = "Avatar"

        coEvery {
            repository.getMovies()
        } returns NetworkResult.Success(defaultMovies)

        every {
            moviesUiItemMapper.map(defaultMovies)
        } returns moviesUiState

        coExcludeRecords {
            repository.getMovies()
            moviesUiItemMapper.map(defaultMovies)
        }

        coEvery {
            repository.searchMovies(query)
        } returns NetworkResult.Success(defaultSearchItems)

        every {
            searchUiItemMapper.map(defaultSearchItems)
        } returns searchUiState

        val viewModel = MoviesViewModel(
            repository = repository,
            moviesUiItemMapper = moviesUiItemMapper,
            searchUiItemMapper = searchUiItemMapper
        )

        /* When */
        viewModel.onQueryChange(query)

        /* Then */
        viewModel.searchQuery.test {
            assertEquals(query, awaitItem())
        }

        viewModel.moviesStateFlow.test {
            assertEquals(moviesUiState, awaitItem())
            assertEquals(MoviesUiState.Loading, awaitItem())
            assertEquals(searchUiState, awaitItem())
        }

        coVerifySequence {
            repository.searchMovies(query)
            searchUiItemMapper.map(defaultSearchItems)
        }
    }

    @Test
    @DisplayName("Проверка ошибки при поиске фильмов")
    fun test_onQueryChange_error() = runTest {
        /* Given */
        val query = "Avatar"

        coEvery {
            repository.getMovies()
        } returns NetworkResult.Success(defaultMovies)

        every {
            moviesUiItemMapper.map(defaultMovies)
        } returns moviesUiState

        coExcludeRecords {
            repository.getMovies()
            moviesUiItemMapper.map(defaultMovies)
        }

        coEvery {
            repository.searchMovies(query)
        } returns NetworkResult.Error(Throwable("Test exception"))

        val viewModel = MoviesViewModel(
            repository = repository,
            moviesUiItemMapper = moviesUiItemMapper,
            searchUiItemMapper = searchUiItemMapper
        )

        /* When */
        viewModel.onQueryChange(query)

        /* Then */
        viewModel.searchQuery.test {
            assertEquals(query, awaitItem())
        }

        viewModel.moviesStateFlow.test {
            assertEquals(moviesUiState, awaitItem())
            assertEquals(MoviesUiState.Loading, awaitItem())
            assertEquals(MoviesUiState.Error::class, awaitItem()::class)
        }

        coVerifySequence {
            repository.searchMovies(query)
        }
    }

    @Test
    @DisplayName("Проверка очистки поискового запроса")
    fun test_onClearQuery() = runTest() {
        /* Given */
        val query = "Avatar"

        coEvery {
            repository.getMovies()
        } returns NetworkResult.Success(defaultMovies)

        every {
            moviesUiItemMapper.map(defaultMovies)
        } returns moviesUiState

        coEvery {
            repository.searchMovies(query)
        } returns NetworkResult.Success(defaultSearchItems)

        every {
            searchUiItemMapper.map(defaultSearchItems)
        } returns searchUiState

        val viewModel = MoviesViewModel(
            repository = repository,
            moviesUiItemMapper = moviesUiItemMapper,
            searchUiItemMapper = searchUiItemMapper
        ).apply {
            // Устанавливаем значение поиска
            onQueryChange(query)
            advanceTimeBy(501)

            searchQuery.test {
                assertEquals(query, awaitItem())
            }
            moviesStateFlow.test {
                assertEquals(searchUiState, awaitItem())
            }

        }

        /* When */
        viewModel.onClearQuery()

        /* Then */
        viewModel.searchQuery.test {
            assertEquals("", awaitItem())
        }
        viewModel.moviesStateFlow.test {
            assertEquals(moviesUiState, awaitItem())
        }

        coVerifySequence {
            repository.getMovies()
            repository.searchMovies(query)
            repository.getMovies()
        }
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

        val moviesUiState = MoviesUiState.Movies(defaultMoviesUIItems)

        val defaultSearchItems = listOf(
            SearchItem(
                id = 1,
                name = "Avatar",
                imageUrl = "imageUrl",
                year = "2009"
            )
        )

        val defaultSearchUiItems = listOf(
            SearchUiItem(
                id = 1,
                name = "Avatar",
                imageUrl = "imageUrl",
                year = "2009"
            )
        )

        val searchUiState = MoviesUiState.SearchResult(defaultSearchUiItems)
    }
}
