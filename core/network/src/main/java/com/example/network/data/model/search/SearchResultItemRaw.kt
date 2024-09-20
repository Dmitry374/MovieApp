package com.example.network.data.model.search

import com.example.model.domain.SearchResultItem
import com.example.network.data.mapper.NetworkResultMapper
import kotlinx.serialization.Serializable
import javax.inject.Inject

@Serializable
class SearchResultItemRaw(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val year: Int
) {
    class SearchResultItemRawMapper @Inject constructor() :
        NetworkResultMapper<SearchResultItemRaw, SearchResultItem>() {
        override fun map(raw: SearchResultItemRaw): SearchResultItem {
            return SearchResultItem(
                id = raw.id,
                name = raw.name,
                imageUrl = raw.imageUrl,
                year = raw.year
            )
        }
    }
}
