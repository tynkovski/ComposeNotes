package com.tynkovski.notes.data.remote.requests

import kotlinx.serialization.Serializable

@Serializable
data class NoteRequest(
    val text: String,
    val title: String,
    val color: Long,
    val tags: List<String>,
)
