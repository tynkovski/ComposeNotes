package com.tynkovski.notes.data.local.repository

import com.tynkovski.notes.data.local.dao.NoteDao
import com.tynkovski.notes.data.local.mappers.noteMapper
import com.tynkovski.notes.domain.models.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface LocalNoteRepository {

    fun getNotes(): Flow<List<Note>>

    fun addNotes(vararg note: Note)

    fun deleteNotes(vararg note: Note)

    fun updateNote(note: Note)

}

class LocalNoteRepositoryImpl(
    private val noteDao: NoteDao
) : LocalNoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return noteDao.getNotesFlow().map(::noteMapper)
    }

    override fun addNotes(vararg note: Note) {
        noteDao.addNotes(note.asList().map(::noteMapper))
    }

    override fun deleteNotes(vararg note: Note) {
        noteDao.deleteNotes(note.asList().map(::noteMapper))
    }

    override fun updateNote(note: Note) {
        noteDao.updateNote(noteMapper(note))
    }
}
