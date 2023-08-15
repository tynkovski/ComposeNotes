package com.tynkovski.notes.data.remote.services

import com.tynkovski.notes.data.remote.models.NetResult
import com.tynkovski.notes.data.remote.models.toNetResult
import com.tynkovski.notes.data.remote.requests.NoteRequest
import com.tynkovski.notes.data.remote.responses.NoteResponse
import com.tynkovski.notes.data.remote.responses.NotesResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody

interface NoteApi {
    suspend fun getPaged(
        limit: Int,
        offset: Int,
        sort: String
    ): NetResult<NotesResponse>

    suspend fun getNotes(sort: String): NetResult<NotesResponse>

    suspend fun getNote(id: String): NetResult<NoteResponse>

    suspend fun deleteNote(id: String): NetResult<NoteResponse>

    suspend fun createNote(
        text: String,
        title: String,
        color: Long,
        tags: List<String>
    ): NetResult<NoteResponse>

    suspend fun updateNote(
        text: String,
        title: String,
        color: Long,
        tags: List<String>
    ): NetResult<NoteResponse>
}

class NoteApiImpl(
    private val client: HttpClient
) : NoteApi {
    override suspend fun getPaged(
        limit: Int,
        offset: Int,
        sort: String
    ): NetResult<NotesResponse> {
        return client.get("/notes/paged") {
            parameter("limit", limit)
            parameter("offset", offset)
            parameter("sort", sort)
        }.toNetResult<NotesResponse>()
    }

    override suspend fun getNotes(
        sort: String
    ): NetResult<NotesResponse> {
        return client.get("/notes/get") {
            parameter("sort", sort)
        }.toNetResult<NotesResponse>()
    }

    override suspend fun getNote(id: String): NetResult<NoteResponse> {
        return client.get("/note/get") {
            parameter("id", id)
        }.toNetResult<NoteResponse>()
    }

    override suspend fun deleteNote(id: String): NetResult<NoteResponse> {
        return client.delete("/note/delete") {
            parameter("id", id)
        }.toNetResult<NoteResponse>()
    }

    override suspend fun createNote(
        text: String,
        title: String,
        color: Long,
        tags: List<String>
    ): NetResult<NoteResponse> {
        return client.post("/note/create") {
            setBody(NoteRequest(text, title, color, tags))
        }.toNetResult<NoteResponse>()
    }

    override suspend fun updateNote(
        text: String,
        title: String,
        color: Long,
        tags: List<String>
    ): NetResult<NoteResponse> {
        return client.put("/note/update") {
            setBody(NoteRequest(text, title, color, tags))
        }.toNetResult<NoteResponse>()
    }
}