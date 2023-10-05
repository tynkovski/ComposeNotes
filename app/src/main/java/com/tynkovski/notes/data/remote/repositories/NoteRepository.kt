package com.tynkovski.notes.data.remote.repositories

import com.tynkovski.notes.data.remote.mappers.noteMapper
import com.tynkovski.notes.data.remote.mappers.unitMapper
import com.tynkovski.notes.data.remote.models.LocalResult
import com.tynkovski.notes.data.remote.models.toResult
import com.tynkovski.notes.data.remote.services.NoteApi
import com.tynkovski.notes.domain.models.Note
import com.tynkovski.notes.domain.models.NoteSort
import com.tynkovski.notes.domain.models.NoteWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface NoteRepository {
    suspend fun getNotes(sort: NoteSort): Flow<LocalResult<NoteWrapper>>

    suspend fun getNote(id: String): Flow<LocalResult<Note>>

    suspend fun deleteNote(id: String): Flow<LocalResult<Unit>>

    suspend fun deleteNotes(ids: List<String>): Flow<LocalResult<Unit>>

    suspend fun createNote(
        text: String,
        title: String,
        color: Long
    ): Flow<LocalResult<Note>>

    suspend fun updateNote(
        noteId: String,
        text: String,
        title: String,
        color: Long
    ): Flow<LocalResult<Note>>
}

class NoteRepositoryImpl(
    private val api: NoteApi
) : NoteRepository {
    override suspend fun getNotes(sort: NoteSort): Flow<LocalResult<NoteWrapper>> {
        return flow {
            emit(api.getNotes(sort.toString()))
        }.toResult(::noteMapper)
    }

    override suspend fun getNote(id: String): Flow<LocalResult<Note>> {
        return flow {
            emit(api.getNote(id))
        }.toResult(::noteMapper)
    }

    override suspend fun deleteNote(id: String): Flow<LocalResult<Unit>> {
        return flow {
            emit(api.deleteNote(id))
        }.toResult(::unitMapper)
    }

    override suspend fun deleteNotes(ids: List<String>): Flow<LocalResult<Unit>> {
        return flow {
            emit(api.deleteNotes(ids))
        }.toResult(::unitMapper)
    }

    override suspend fun createNote(
        text: String,
        title: String,
        color: Long,
    ): Flow<LocalResult<Note>> {
        return flow {
            emit(api.createNote(text, title, color))
        }.toResult(::noteMapper)
    }

    override suspend fun updateNote(
        noteId: String,
        text: String,
        title: String,
        color: Long,
    ): Flow<LocalResult<Note>> {
        return flow {
            emit(api.updateNote(noteId, text, title, color))
        }.toResult(::noteMapper)
    }
}