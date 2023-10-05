package com.tynkovski.notes.di

import com.tynkovski.notes.presentation.activities.MainViewModel
import com.tynkovski.notes.presentation.pages.authorization.signIn.SignInViewModel
import com.tynkovski.notes.presentation.pages.authorization.signUp.SignUpViewModel
import com.tynkovski.notes.presentation.pages.detail.remote.RemoteDetailNoteViewModel
import com.tynkovski.notes.presentation.pages.notes.remote.RemoteNotesViewModel
import com.tynkovski.notes.presentation.pages.settings.screen.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::SignInViewModel)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::RemoteNotesViewModel)
    viewModel { (noteId: String) -> RemoteDetailNoteViewModel(noteId = noteId, get()) }
}