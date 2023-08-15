package com.tynkovski.notes.data.remote.mappers

import com.tynkovski.notes.data.remote.responses.TokenResponse
import com.tynkovski.notes.domain.models.Token

fun tokenMapper(response: TokenResponse): Token = Token(token = response.token)