package com.example.network.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ReleasesResponse(
    val releases: List<ReleaseRaw>
)
