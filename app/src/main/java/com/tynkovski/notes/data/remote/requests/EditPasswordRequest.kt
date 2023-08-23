package com.tynkovski.notes.data.remote.requests

import kotlinx.serialization.Serializable

@Serializable
data class EditPasswordRequest(
    val oldPassword: String,
    val newPassword: String,
)