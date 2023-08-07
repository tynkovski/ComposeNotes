package com.tynkovski.notes.presentation.pages.authorization.signIn

import androidx.compose.ui.text.input.TextFieldValue

data class SignInInputState(
    val login: TextFieldValue = TextFieldValue(""),
    val password: TextFieldValue = TextFieldValue(""),
)