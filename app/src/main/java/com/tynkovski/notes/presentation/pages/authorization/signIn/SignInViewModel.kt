package com.tynkovski.notes.presentation.pages.authorization.signIn

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class SignInViewModel(
    // private val authRepository: IAuthRepository,
) : ViewModel(), ContainerHost<SignInViewModel.State, SignInViewModel.SideEffect> {
    override val container = container<State, SideEffect>(State(loading = false))

    private val _inputState = MutableStateFlow(SignInInputState())
    val input = _inputState.asStateFlow()

    fun inputLoginChanged(login: TextFieldValue) {
        _inputState.value = _inputState.value.copy(login = login)
    }

    fun inputPasswordChanged(password: TextFieldValue) {
        _inputState.value = _inputState.value.copy(password = password)
    }

    fun enter() {
//        authRepository
//            .login(
//                input.value.login.text,
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

    class State(val loading: Boolean)

    sealed interface SideEffect {
        object UserLogin: SideEffect
    }
}