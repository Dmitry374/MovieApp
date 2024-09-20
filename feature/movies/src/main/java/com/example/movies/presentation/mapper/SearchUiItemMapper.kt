package com.example.movies.presentation.mapper

import com.example.model.domain.SearchItem
import com.example.movies.presentation.model.MoviesUiState
import com.example.movies.presentation.model.SearchUiItem
import javax.inject.Inject

class SearchUiItemMapper @Inject constructor() {

    fun map(searchItems: List<SearchItem>): MoviesUiState {
        return if (searchItems.isNotEmpty()) {
            val uiItems = mapSearchResultItems(searchItems)
            MoviesUiState.SearchResult(uiItems)
        } else {
            MoviesUiState.Empty
        }
    }

    private fun mapSearchResultItems(searchItems: List<SearchItem>): List<SearchUiItem> {
        return searchItems.map(::mapSearchResultItem)
    }

    private fun mapSearchResultItem(item: SearchItem): SearchUiItem {
        return SearchUiItem(
            id = item.id,
            name = item.name,
            imageUrl = item.imageUrl,
            year = item.year
        )
    }
}
