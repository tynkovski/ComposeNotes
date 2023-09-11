package com.tynkovski.notes.data.remote.responses

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: String,
    val login: String,
    val name: String,
    val createdAt: Long
)