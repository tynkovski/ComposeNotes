package com.tynkovski.notes.data.remote.requests

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val login: String,
    val password: String,
)