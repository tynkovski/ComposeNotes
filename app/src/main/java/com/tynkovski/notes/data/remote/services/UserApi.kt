package com.tynkovski.notes.data.remote.services

import com.tynkovski.notes.data.remote.models.NetResult
import com.tynkovski.notes.data.remote.models.toNetResult
import com.tynkovski.notes.data.remote.requests.EditNameRequest
import com.tynkovski.notes.data.remote.requests.EditPasswordRequest
import com.tynkovski.notes.data.remote.responses.UserResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody

interface UserApi {
    suspend fun getUser(): NetResult<UserResponse>

    suspend fun deleteUser(): NetResult<Unit>

    suspend fun changePassword(oldPassword: String, newPassword: String): NetResult<Unit>

    suspend fun changeName(name: String): NetResult<Unit>
}

class UserApiImpl(
    private val client: HttpClient
) : UserApi {
    override suspend fun getUser(): NetResult<UserResponse> {
        return client.get("/user/get").toNetResult<UserResponse>()
    }

    override suspend fun deleteUser(): NetResult<Unit> {
        return client.delete("/user/delete").toNetResult<Unit>()
    }

    override suspend fun changePassword(oldPassword: String, newPassword: String): NetResult<Unit> {
        return client.post("/user/password") {
            setBody(EditPasswordRequest(oldPassword, newPassword))
        }.toNetResult<Unit>()
    }

    override suspend fun changeName(name: String): NetResult<Unit> {
        return client.post("/user/name") {
            setBody(EditNameRequest(name))
        }.toNetResult<Unit>()
    }
}