package com.tynkovski.notes.data.local.mappers

import com.tynkovski.notes.data.local.entities.UserEntity
import com.tynkovski.notes.domain.models.User

fun userMapper(entity: UserEntity): User = User(
    id = entity.id,
    login = entity.login,
    name = entity.name,
    createdAt = entity.createdAt,
)

fun userMapper(user: User): UserEntity = UserEntity(
    id = user.id,
    login = user.login,
    name = user.name,
    createdAt = user.createdAt
)