package com.tynkovski.notes.di

import com.tynkovski.notes.data.remote.services.AuthApi
import com.tynkovski.notes.data.remote.services.AuthApiImpl
import com.tynkovski.notes.data.remote.services.NoteApi
import com.tynkovski.notes.data.remote.services.NoteApiImpl
import com.tynkovski.notes.data.remote.services.UserApi
import com.tynkovski.notes.data.remote.services.UserApiImpl
import io.ktor.client.HttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module

val serviceModule = module {
    factory<AuthApi> { AuthApiImpl(get<HttpClient>(named(REST_AUTH))) }

    factory<UserApi> { UserApiImpl(get<HttpClient>(named(REST_BASE))) }

    factory<NoteApi> { NoteApiImpl(get<HttpClient>(named(REST_BASE))) }
}