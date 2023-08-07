package com.tynkovski.notes.presentation.pages.authorization.signIn

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

enum class SignInTab {
    Login, Password
}

class SignInViewModel : ViewModel(),
    ContainerHost<SignInViewModel.State, SignInViewModel.SideEffect> {
    override val container = container<State, SideEffect>(
        State(tab = SignInTab.Login, loading = false)
    )

    private val _inputState = MutableStateFlow(SignInInputState())
    val input = _inputState.asStateFlow()

    fun inputLoginChanged(login: TextFieldValue) {
        _inputState.value = _inputState.value.copy(login = login)
    }

    fun inputPasswordChanged(password: TextFieldValue) {
        _inputState.value = _inputState.value.copy(password = password)
    }

    fun toPasswordTab() = intent {
        reduce { state.copy(tab = SignInTab.Password) }
    }

    fun toLoginTab() = intent {
        reduce { state.copy(tab = SignInTab.Login) }
    }

    fun enter() = intent {
        // todo login via backend
    }

    data class State(
        val tab: SignInTab,
        val loading: Boolean
    )

    sealed interface SideEffect
}