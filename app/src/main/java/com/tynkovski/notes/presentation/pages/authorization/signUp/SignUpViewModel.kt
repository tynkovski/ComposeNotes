package com.tynkovski.notes.presentation.pages.authorization.signUp

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class SignUpViewModel(
    //private val authRepository: IAuthRepository,
) : ViewModel(),
    ContainerHost<SignUpViewModel.State, SignUpViewModel.SideEffect> {
    override val container = container<State, SideEffect>(State(loading = false))

    private val _inputState = MutableStateFlow(SignUpInputState())
    val input = _inputState.asStateFlow()

    fun inputLoginChanged(login: TextFieldValue) {
        _inputState.value = _inputState.value.copy(login = login)
    }

    fun inputNameChanged(name: TextFieldValue) {
        _inputState.value = _inputState.value.copy(name = name)
    }

    fun inputPasswordChanged(password: TextFieldValue) {
        _inputState.value = _inputState.value.copy(password = password)
    }

    fun inputRepeatedPasswordChanged(repeatedPassword: TextFieldValue) {
        _inputState.value = _inputState.value.copy(repeatedPassword = repeatedPassword)
    }

    fun signUp() {
//        authRepository
//            .register(
//                input.value.login.text,
//                input.value.name.text,
//                input.value.password.text,
//            )
//            .onSuccess {
//                Log.d("Auth", "Success")
//            }
//            .onError {
//                Log.d("Auth", "Error ${it.message}")
//            }
//            .collect()
    }

    data class State(val loading: Boolean)

    sealed interface SideEffect {
        object UserRegister: SideEffect
    }
}