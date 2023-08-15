package com.tynkovski.notes.data.remote.mappers

import com.tynkovski.notes.data.remote.responses.NoteResponse
import com.tynkovski.notes.data.remote.responses.NotesResponse
import com.tynkovski.notes.domain.models.Note
import com.tynkovski.notes.domain.models.NoteWrapper

fun noteMapper(response: NoteResponse): Note = Note(
    id = response.id,
    text = response.text,
    title = response.title,
    color = response.color,
    createdAt = response.createdAt,
    updatedAt = response.updatedAt
)

fun noteMapper(response: NotesResponse): NoteWrapper = NoteWrapper(
    count = response.count,
    notes = response.notes.map(::noteMapper)
)