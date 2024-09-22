package com.example.movies.data

import com.example.common.domain.NetworkResult
import com.example.model.domain.Movie
import com.example.model.domain.SearchItem
import com.example.network.data.MovieApi
import com.example.network.data.model.release.ReleaseRaw
import com.example.network.data.model.release.ReleasesResponse
import com.example.network.data.model.search.SearchItemRaw
import com.example.network.data.model.search.SearchResponse
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.Extensions

@Extensions(ExtendWith(MockKExtension::class))
class MoviesRepositoryTest(
    @MockK private val movieApi: MovieApi,
    @MockK private val releasesResponseMapper: ReleasesResponse.ReleasesResponseMapper,
    @MockK private val searchResponseMapper: SearchResponse.SearchResponseMapper
) {
    private val repository = MoviesRepositoryImpl(
        movieApi = movieApi,
        releasesResponseMapper = releasesResponseMapper,
        searchResponseMapper = searchResponseMapper
    )

    @Test
    @DisplayName("Проверка успешного получения фильмов")
    fun test_getMovies_success() = runTest {
        /* Given */
        val expectedResult = NetworkResult.Success(defaultMovies)

        coEvery {
            movieApi.getReleases()
        } returns NetworkResult.Success(defaultReleasesResponse)

        every {
            releasesResponseMapper.map(defaultReleasesResponse)
        } returns defaultMovies

        /* When */
        val result = repository.getMovies()

        /* Then */
        assertEquals(expectedResult, result)

        coVerifySequence {
            movieApi.getReleases()
            releasesResponseMapper.map(defaultReleasesResponse)
        }
    }

    @Test
    @DisplayName("Проверка ошибки при получении фильмов")
    fun test_getMovies_error() = runTest {
        /* Given */
        val expectedException = Throwable("Test exception")
        val expectedResult = NetworkResult.Error(expectedException)

        coEvery {
            movieApi.getReleases()
        } returns expectedResult

        /* When */
        val result = repository.getMovies()

        /* Then */
        assertEquals(expectedResult, result)

        coVerifySequence {
            movieApi.getReleases()
        }
    }

    @Test
    @DisplayName("Проверка успешного поиска фильмов")
    fun test_searchMovies_success() = runTest {
        /* Given */
        val query = "Avatar"
        val expectedResult = NetworkResult.Success(defaultSearchItems)

        coEvery {
            movieApi.searchMovies(query)
        } returns NetworkResult.Success(defaultSearchResponse)

        every {
            searchResponseMapper.map(defaultSearchResponse)
        } returns defaultSearchItems

        /* When */
        val result = repository.searchMovies(query)

        /* Then */
        assertEquals(expectedResult, result)

        coVerifySequence {
            movieApi.searchMovies(query)
            searchResponseMapper.map(defaultSearchResponse)
        }
    }

    @Test
    @DisplayName("Проверка ошибки при поиске фильмов")
    fun test_searchMovies_error() = runTest {
        /* Given */
        val query = "Avatar"
        val expectedException = Throwable("Test exception")
        val expectedResult = NetworkResult.Error(expectedException)

        coEvery {
            movieApi.searchMovies(query)
        } returns expectedResult

        /* When */
        val result = repository.searchMovies(query)

        /* Then */
        assertEquals(expectedResult, result)

        coVerifySequence {
            movieApi.searchMovies(query)
        }
    }

    private companion object {
        val defaultReleasesResponse = ReleasesResponse(
            releases = listOf(
                ReleaseRaw(
                    id = 1,
                    posterUrl = "posterUrl",
                    seasonNumber = null,
                    sourceName = "20th Century Fox",
                    sourceReleaseDate = "2009-12-18",
                    title = "Avatar"
                )
            )
        )

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

        val defaultSearchResponse = SearchResponse(
            results = listOf(
                SearchItemRaw(
                    id = 1,
                    name = "Avatar",
                    imageUrl = "imageUrl",
                    year = "2009"
                )
            )
        )

        val defaultSearchItems = listOf(
            SearchItem(
                id = 1,
                name = "Avatar",
                imageUrl = "imageUrl",
                year = "2009"
            )
        )
    }
}
