package com.tynkovski.notes.di

import com.tynkovski.notes.data.local.holders.TokenHolder
import com.tynkovski.notes.data.local.holders.TokenHolderImpl
import com.tynkovski.notes.data.local.preferences.LocalPreferences
import com.tynkovski.notes.data.remote.repositories.AuthRepository
import com.tynkovski.notes.data.remote.repositories.AuthRepositoryImpl
import com.tynkovski.notes.data.remote.repositories.NoteRepository
import com.tynkovski.notes.data.remote.repositories.NoteRepositoryImpl
import com.tynkovski.notes.data.remote.repositories.UserRepository
import com.tynkovski.notes.data.remote.repositories.UserRepositoryImpl
import com.tynkovski.notes.data.remote.services.AuthApi
import com.tynkovski.notes.data.remote.services.NoteApi
import com.tynkovski.notes.data.remote.services.UserApi
import org.koin.dsl.module

val repositoryModule = module {
    factory<UserRepository> { UserRepositoryImpl(get<UserApi>()) }

    factory<AuthRepository> { AuthRepositoryImpl(get<AuthApi>()) }

    factory<NoteRepository> { NoteRepositoryImpl(get<NoteApi>()) }

    factory<TokenHolder> { TokenHolderImpl(get<LocalPreferences>()) }
}