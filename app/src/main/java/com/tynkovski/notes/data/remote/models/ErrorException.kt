package com.tynkovski.notes.data.remote.models

import kotlinx.serialization.SerializationException

sealed class ErrorException : Throwable() {
    data class Serialization(
        override val message: String?
    ) : ErrorException()

    data class NetError(
        val code: Int,
        override val message: String
    ) : ErrorException()

    object Unknown : ErrorException()
}

fun Throwable.toErrorException(): ErrorException = when (val error = this) {
    is ErrorException.NetError -> error
    is SerializationException -> ErrorException.Serialization(error.message)
    else -> ErrorException.Unknown
}