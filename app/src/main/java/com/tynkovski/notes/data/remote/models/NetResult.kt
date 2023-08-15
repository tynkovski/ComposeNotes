package com.tynkovski.notes.data.remote.models

import com.tynkovski.notes.data.remote.responses.ErrorResponse
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

sealed interface NetResult<out T> {

    data class Error(
        val code: Int,
        val message: String,
    ) : NetResult<Nothing>

    data class Success<T>(
        val value: T,
    ) : NetResult<T>
}

suspend inline fun <reified T> HttpResponse.toNetResult(): NetResult<T> = when (status) {
    HttpStatusCode.OK -> NetResult.Success(body())

    HttpStatusCode.NoContent,
    HttpStatusCode.Unauthorized,
    HttpStatusCode.Forbidden,
    HttpStatusCode.NotFound,
    HttpStatusCode.InternalServerError,
    HttpStatusCode.BadRequest,
    HttpStatusCode.TooManyRequests -> {
        val message = runCatching {
            body<ErrorResponse>().messageDetail
        }.getOrDefault("Serialization error")

        NetResult.Error(
            code = status.value,
            message = message
        )
    }

    else -> NetResult.Error(status.value, "Unknown Error")
}