package com.tynkovski.notes.data.remote.requests

import kotlinx.serialization.Serializable

@Serializable
class EditNameRequest(
    val name: String
)