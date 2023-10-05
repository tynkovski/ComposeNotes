package com.tynkovski.notes.data.remote.requests

import kotlinx.serialization.Serializable

@Serializable
data class IdsRequest(
    val ids: List<String>
)
