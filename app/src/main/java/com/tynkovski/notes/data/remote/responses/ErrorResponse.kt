package com.tynkovski.notes.data.remote.responses

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val errors: Map<String, List<String>>
) {
    val messageDetail get() = this.errors.entries.joinToString(". ") {
        "${it.key}: ${it.value.joinToString(", ")}"
    }
}