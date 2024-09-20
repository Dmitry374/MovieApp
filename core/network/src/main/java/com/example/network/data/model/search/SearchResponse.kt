package com.example.network.data.model.search

import com.example.model.domain.SearchItem
import com.example.network.data.mapper.NetworkResultMapper
import kotlinx.serialization.Serializable
import javax.inject.Inject

@Serializable
class SearchResponse(
    val results: List<SearchItemRaw>
) {
    class SearchResponseMapper @Inject constructor(
        private val searchItemRawMapper: SearchItemRaw.SearchItemRawMapper
    ) : NetworkResultMapper<SearchResponse, List<SearchItem>>() {
        override fun map(raw: SearchResponse): List<SearchItem> {
            return raw.results.map(searchItemRawMapper::map)
        }
    }
}
