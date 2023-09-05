package com.tynkovski.notes.di

import com.tynkovski.notes.presentation.activities.MainViewModel
import com.tynkovski.notes.presentation.pages.authorization.signIn.SignInViewModel
import com.tynkovski.notes.presentation.pages.authorization.signUp.SignUpViewModel
import com.tynkovski.notes.presentation.pages.detailNote.screen.DetailNoteViewModel
import com.tynkovski.notes.presentation.pages.notes.screen.NotesViewModel
import com.tynkovski.notes.presentation.pages.settings.screen.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::SignInViewModel)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::NotesViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::DetailNoteViewModel)
}