package com.tynkovski.notes.data.remote.repositories

import com.tynkovski.notes.data.remote.mappers.unitMapper
import com.tynkovski.notes.data.remote.mappers.userMapper
import com.tynkovski.notes.data.remote.models.LocalResult
import com.tynkovski.notes.data.remote.models.toResult
import com.tynkovski.notes.data.remote.services.UserApi
import com.tynkovski.notes.domain.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface UserRepository {
    suspend fun getUser(): Flow<LocalResult<User>>

    suspend fun deleteUser(): Flow<LocalResult<Unit>>

    suspend fun changePassword(oldPassword: String, newPassword: String): Flow<LocalResult<Unit>>
}

class UserRepositoryImpl(
    private val api: UserApi
) : UserRepository {
    override suspend fun getUser(): Flow<LocalResult<User>> {
        return flow {
            emit(api.getUser())
        }.toResult(::userMapper)
    }

    override suspend fun deleteUser(): Flow<LocalResult<Unit>> {
        return flow {
            emit(api.deleteUser())
        }.toResult(::unitMapper)
    }

    override suspend fun changePassword(
        oldPassword: String,
        newPassword: String
    ): Flow<LocalResult<Unit>> {
        return flow {
            emit(api.changePassword(oldPassword, newPassword))
        }.toResult(::unitMapper)
    }
}