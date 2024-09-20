package com.example.network.data.model.search

import com.example.model.domain.SearchResultItem
import com.example.network.data.mapper.NetworkResultMapper
import kotlinx.serialization.Serializable
import javax.inject.Inject

@Serializable
class SearchResponse(
    val results: List<SearchResultItemRaw>
) {
    class SearchResponseMapper @Inject constructor(
        private val searchResultItemRawMapper: SearchResultItemRaw.SearchResultItemRawMapper
    ) : NetworkResultMapper<SearchResponse, List<SearchResultItem>>() {
        override fun map(raw: SearchResponse): List<SearchResultItem> {
            return raw.results.map(searchResultItemRawMapper::map)
        }
    }
}
