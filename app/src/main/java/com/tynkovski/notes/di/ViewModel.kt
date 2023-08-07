package com.tynkovski.notes.di

import com.tynkovski.notes.presentation.pages.authorization.signIn.SignInViewModel
import com.tynkovski.notes.presentation.pages.authorization.signUp.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::SignInViewModel)
    viewModelOf(::SignUpViewModel)
}