package com.tynkovski.notes.presentation.pages.authorization.signUp

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

enum class SignUpTab {
    Login, Name, Password
}

class SignUpViewModel(
    //private val authRepository: IAuthRepository,
) : ViewModel(),
    ContainerHost<SignUpViewModel.State, SignUpViewModel.SideEffect> {
    override val container = container<State, SideEffect>(State(tab = SignUpTab.Login))

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

    fun toLoginTab() = intent {
        reduce { state.copy(tab = SignUpTab.Login) }
    }

    fun toNameTab() = intent {
        reduce { state.copy(tab = SignUpTab.Name) }
    }

    fun toPasswordTab() = intent {
        reduce { state.copy(tab = SignUpTab.Password) }
    }

    fun signUp() = intent {
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

    data class State(
        val tab: SignUpTab,
    )

    sealed interface SideEffect
}