package com.tynkovski.notes.data.local.mappers

import com.tynkovski.notes.data.local.entities.NoteEntity
import com.tynkovski.notes.domain.models.Note

fun noteMapper(entity: NoteEntity): Note = Note(
    id = entity.id,
    text = entity.text,
    title = entity.title,
    color = entity.color,
    createdAt = entity.createdAt,
    updatedAt = entity.updatedAt
)

fun noteMapper(note: Note): NoteEntity = NoteEntity(
    id = note.id,
    text = note.text,
    title = note.title,
    color = note.color,
    createdAt = note.createdAt,
    updatedAt = note.updatedAt
)