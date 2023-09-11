package com.tynkovski.notes.domain.models

data class User(
    val id: String,
    val login: String,
    val name: String,
    val createdAt: Long
)