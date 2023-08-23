package com.tynkovski.notes.presentation.pages.authorization.state

import androidx.compose.ui.text.input.TextFieldValue

data class SignUpInputState(
    val login: TextFieldValue = TextFieldValue(""),
    val name: TextFieldValue = TextFieldValue(""),
    val password: TextFieldValue = TextFieldValue(""),
    val repeatedPassword: TextFieldValue = TextFieldValue(""),
)