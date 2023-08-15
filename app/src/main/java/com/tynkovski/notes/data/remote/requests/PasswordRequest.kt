package com.tynkovski.notes.data.remote.requests

import kotlinx.serialization.Serializable

@Serializable
data class PasswordRequest(
    val oldPassword: String,
    val newPassword: String,
)