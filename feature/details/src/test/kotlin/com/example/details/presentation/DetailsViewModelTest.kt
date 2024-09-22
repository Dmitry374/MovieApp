package com.example.details.presentation

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.details.domain.DetailsRepository
import com.example.details.presentation.mapper.DetailsUiStateMapper
import com.example.details.presentation.model.DetailsUiState
import com.example.model.domain.MovieDetail
import io.mockk.coJustRun
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.excludeRecords
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
class DetailsViewModelTest(
    @MockK private val repository: DetailsRepository,
    @MockK private val savedStateHandle: SavedStateHandle,
    @MockK private val detailsUiStateMapper: DetailsUiStateMapper
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
    @DisplayName("Проверка успешного получения деталей")
    fun test_listenFavoriteMovies_success() = runTest {
        /* Given */
        val titleId = 123L

        every {
            savedStateHandle.get<String>("titleId")
        } returns titleId.toString()

        every {
            repository.listenMovieDetails(titleId)
        } returns flowOf(defaultMovieDetail)

        every {
            detailsUiStateMapper.map(defaultMovieDetail)
        } returns defaultDetailsUiState

        /* When */
        val viewModel = DetailsViewModel(
            repository = repository,
            savedStateHandle = savedStateHandle,
            detailsUiStateMapper = detailsUiStateMapper
        )

        /* Then */
        viewModel.detailsStateFlow.test {
            assertEquals(defaultDetailsUiState, awaitItem())
        }

        verifySequence {
            savedStateHandle.get<String>("titleId")
            repository.listenMovieDetails(titleId)
            detailsUiStateMapper.map(defaultMovieDetail)
        }
    }

    @Test
    @DisplayName("Проверка ошибки при получении деталей")
    fun test_listenFavoriteMovies_error() = runTest {
        /* Given */
        val titleId = 123L
        val testException = Throwable("Test exception")

        every {
            savedStateHandle.get<String>("titleId")
        } returns titleId.toString()

        every {
            repository.listenMovieDetails(titleId)
        } returns flow {
            throw testException
        }

        /* When */
        val viewModel = DetailsViewModel(
            repository = repository,
            savedStateHandle = savedStateHandle,
            detailsUiStateMapper = detailsUiStateMapper
        )

        /* Then */
        viewModel.detailsStateFlow.test {
            assertEquals(DetailsUiState.Error::class, awaitItem()::class)
        }

        verifySequence {
            savedStateHandle.get<String>("titleId")
            repository.listenMovieDetails(titleId)
        }
    }

    @Test
    @DisplayName("Проверка добавления фильма в избранные")
    fun test_onFavoriteClick_addFavorite() = runTest {
        /* Given */
        val titleId = 123L
        val currentUiState = defaultDetailsUiState.copy(
            id = titleId,
            isFavorite = false
        )
        val movieDetail = defaultMovieDetail.copy(
            id = titleId,
            isFavorite = false
        )

        every {
            savedStateHandle.get<String>("titleId")
        } returns titleId.toString()

        every {
            repository.listenMovieDetails(titleId)
        } returns flowOf(defaultMovieDetail)

        every {
            detailsUiStateMapper.map(defaultMovieDetail)
        } returns currentUiState

        excludeRecords {
            savedStateHandle.get<String>("titleId")
            repository.listenMovieDetails(titleId)
            detailsUiStateMapper.map(defaultMovieDetail)
        }

        every {
            detailsUiStateMapper.map(currentUiState)
        } returns movieDetail

        coJustRun {
            repository.addFavorite(movieDetail)
        }

        val viewModel = DetailsViewModel(
            repository = repository,
            savedStateHandle = savedStateHandle,
            detailsUiStateMapper = detailsUiStateMapper
        )

        /* When */
        viewModel.onFavoriteClick(currentUiState)

        /* Then */
        coVerifySequence {
            detailsUiStateMapper.map(currentUiState)
            repository.addFavorite(movieDetail)
        }
    }

    @Test
    @DisplayName("Проверка удаление фильма из избранных")
    fun test_onFavoriteClick_deleteFavorite() = runTest {
        /* Given */
        val titleId = 123L
        val currentUiState = defaultDetailsUiState.copy(
            id = titleId,
            isFavorite = true
        )

        every {
            savedStateHandle.get<String>("titleId")
        } returns titleId.toString()

        every {
            repository.listenMovieDetails(titleId)
        } returns flowOf(defaultMovieDetail)

        every {
            detailsUiStateMapper.map(defaultMovieDetail)
        } returns currentUiState

        excludeRecords {
            savedStateHandle.get<String>("titleId")
            repository.listenMovieDetails(titleId)
            detailsUiStateMapper.map(defaultMovieDetail)
        }

        coJustRun {
            repository.deleteFavorite(titleId)
        }

        val viewModel = DetailsViewModel(
            repository = repository,
            savedStateHandle = savedStateHandle,
            detailsUiStateMapper = detailsUiStateMapper
        )

        /* When */
        viewModel.onFavoriteClick(currentUiState)

        /* Then */
        coVerifySequence {
            repository.deleteFavorite(titleId)
        }
    }

    private companion object {
        val defaultMovieDetail = MovieDetail(
            id = 1,
            title = "Avatar",
            poster = "posterUrl",
            backdrop = "backdropUrl",
            releaseDate = "2009-12-18",
            description = "description",
            rating = 9.2,
            genreNames = listOf("Action"),
            runtimeMinutes = 162,
            isFavorite = false
        )

        val defaultDetailsUiState = DetailsUiState.Success(
            id = 1,
            title = "Avatar",
            posterUrl = "posterUrl",
            backdropUrl = "backdropUrl",
            releaseDate = "2009-12-18",
            description = "description",
            rating = 9.2,
            genres = listOf("Action"),
            runtimeMinutes = 162,
            isFavorite = false
        )
    }
}
