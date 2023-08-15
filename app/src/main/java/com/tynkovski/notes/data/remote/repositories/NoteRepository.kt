package com.tynkovski.notes.data.remote.repositories

import com.tynkovski.notes.data.remote.mappers.noteMapper
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

    suspend fun deleteNote(id: String): Flow<LocalResult<Note>>

    suspend fun createNote(
        text: String,
        title: String,
        color: Long,
        tags: List<String>
    ): Flow<LocalResult<Note>>

    suspend fun updateNote(
        text: String,
        title: String,
        color: Long,
        tags: List<String>
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

    override suspend fun deleteNote(id: String): Flow<LocalResult<Note>> {
        return flow {
            emit(api.deleteNote(id))
        }.toResult(::noteMapper)
    }

    override suspend fun createNote(
        text: String,
        title: String,
        color: Long,
        tags: List<String>
    ): Flow<LocalResult<Note>> {
        return flow {
            emit(api.createNote(text, title, color, tags))
        }.toResult(::noteMapper)
    }

    override suspend fun updateNote(
        text: String,
        title: String,
        color: Long,
        tags: List<String>
    ): Flow<LocalResult<Note>> {
        return flow {
            emit(api.updateNote(text, title, color, tags))
        }.toResult(::noteMapper)
    }
}