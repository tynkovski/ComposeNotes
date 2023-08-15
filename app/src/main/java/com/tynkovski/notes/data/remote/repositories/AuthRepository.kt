package com.tynkovski.notes.data.remote.repositories

import com.tynkovski.notes.data.remote.mappers.tokenMapper
import com.tynkovski.notes.domain.models.Token
import com.tynkovski.notes.data.remote.models.LocalResult
import com.tynkovski.notes.data.remote.models.toResult
import com.tynkovski.notes.data.remote.services.AuthApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface AuthRepository {
    suspend fun register(login: String, password: String): Flow<LocalResult<Token>>

    suspend fun login(login: String, password: String): Flow<LocalResult<Token>>
}

class AuthRepositoryImpl(
    private val api: AuthApi
) : AuthRepository {
    override suspend fun register(login: String, password: String): Flow<LocalResult<Token>> {
        return flow {
            emit(api.register(login, password))
        }.toResult(::tokenMapper)
    }

    override suspend fun login(login: String, password: String): Flow<LocalResult<Token>> {
        return flow {
            emit(api.login(login, password))
        }.toResult(::tokenMapper)
    }
}