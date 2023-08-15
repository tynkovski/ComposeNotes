package com.tynkovski.notes.data.remote.models

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface LocalResult<out T> {
    data class Success<out T>(
        val value: T,
    ) : LocalResult<T>

    object Loading : LocalResult<Nothing>
}

fun <N, R> Flow<NetResult<N>>.toResult(
    mapper: (N) -> R
): Flow<LocalResult<R>> {
    return map { it.toLocalResult(mapper) }
        .onStart { emit(LocalResult.Loading) }
}

fun <N, R> NetResult<N>.toLocalResult(
    mapper: (N) -> R
): LocalResult<R> = when (this) {
    is NetResult.Success -> LocalResult.Success(mapper(value))
    is NetResult.Error -> throw ErrorException.NetError(code, message)
}

fun <T> Flow<LocalResult<T>>.onLoading(
    action: suspend () -> Unit,
): Flow<LocalResult<T>> = flow {
    collect { value ->
        if (value is LocalResult.Loading) action()
        emit(value)
    }
}

fun <T> Flow<LocalResult<T>>.onSuccess(
    action: suspend (value: T) -> Unit,
): Flow<LocalResult<T>> = flow {
    collect { value ->
        if (value is LocalResult.Success) action(value.value)
        emit(value)
    }
}

fun <T> Flow<LocalResult<T>>.onError(
    action: suspend (value: ErrorException) -> Unit,
): Flow<LocalResult<T>> = catch {
    it.printStackTrace()
    action(it.toErrorException())
}