package com.tynkovski.notes.data.remote.services

import com.tynkovski.notes.data.remote.models.NetResult
import com.tynkovski.notes.data.remote.models.toNetResult
import com.tynkovski.notes.data.remote.requests.AuthRequest
import com.tynkovski.notes.data.remote.requests.RegisterRequest
import com.tynkovski.notes.data.remote.responses.TokenResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody

interface AuthApi {
    suspend fun register(login: String, name: String, password: String): NetResult<TokenResponse>

    suspend fun login(login: String, password: String): NetResult<TokenResponse>
}

class AuthApiImpl(
    private val client: HttpClient
) : AuthApi {
    override suspend fun register(
        login: String,
        name: String,
        password: String
    ): NetResult<TokenResponse> {
        return client.post("/auth/register") {
            setBody(RegisterRequest(login, name, password))
        }.toNetResult<TokenResponse>()
    }

    override suspend fun login(login: String, password: String): NetResult<TokenResponse> {
        return client.post("/auth/login") {
            setBody(AuthRequest(login, password))
        }.toNetResult<TokenResponse>()
    }
}