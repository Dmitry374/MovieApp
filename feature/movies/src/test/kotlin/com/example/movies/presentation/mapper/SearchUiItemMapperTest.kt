package com.example.movies.presentation.mapper

import com.example.model.domain.SearchItem
import com.example.movies.presentation.model.MoviesUiState
import com.example.movies.presentation.model.SearchUiItem
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class SearchUiItemMapperTest {

    private val mapper = SearchUiItemMapper()

    @Test
    @DisplayName("Проверка маппинга списка с результатами поиска")
    fun test_map() {
        /* Given */
        val expectedUiState = MoviesUiState.SearchResult(defaultSearchUiItems)

        /* When */
        val result = mapper.map(defaultSearchItems)

        /* Then */
        assertEquals(expectedUiState, result)
    }

    @Test
    @DisplayName("Проверка маппинга пустого списка результатов поиска")
    fun test_map_emptyList() {
        /* Given */
        val searchItems = emptyList<SearchItem>()
        val expectedUiState = MoviesUiState.Empty

        /* When */
        val result = mapper.map(searchItems)

        /* Then */
        assertEquals(expectedUiState, result)
    }

    private companion object {
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
    }
}
