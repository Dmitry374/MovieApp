package com.example.network.data.model.search

import com.example.model.domain.SearchItem
import com.example.network.data.mapper.NetworkResultMapper
import kotlinx.serialization.Serializable
import javax.inject.Inject

@Serializable
class SearchItemRaw(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val year: String
) {
    class SearchItemRawMapper @Inject constructor() :
        NetworkResultMapper<SearchItemRaw, SearchItem>() {
        override fun map(raw: SearchItemRaw): SearchItem {
            return SearchItem(
                id = raw.id,
                name = raw.name,
                imageUrl = raw.imageUrl,
                year = raw.year
            )
        }
    }
}
