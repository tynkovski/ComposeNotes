package com.tynkovski.notes.data.remote.mappers

import com.tynkovski.notes.data.remote.responses.UserResponse
import com.tynkovski.notes.domain.models.User

fun userMapper(user: UserResponse): User = User(
    id = user.id,
    login = user.login,
    createdAt = user.createdAt
)