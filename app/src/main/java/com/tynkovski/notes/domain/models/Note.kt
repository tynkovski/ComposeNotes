package com.tynkovski.notes.domain.models

data class Note(
    val id: String,
    val text: String,
    val title: String?,
    val color: Long?,
    val createdAt: Long,
    val updatedAt: Long,
)

data class NoteWrapper(
    val count: Int,
    val notes: List<Note>
)