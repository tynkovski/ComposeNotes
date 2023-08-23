package com.tynkovski.notes.presentation.pages.authorization.signIn

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.tynkovski.notes.data.remote.models.onError
import com.tynkovski.notes.data.remote.models.onLoading
import com.tynkovski.notes.data.remote.models.onSuccess
import com.tynkovski.notes.data.remote.repositories.AuthRepository
import com.tynkovski.notes.presentation.components.button.ButtonState
import com.tynkovski.notes.presentation.pages.authorization.state.SignInInputState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class SignInViewModel(
    private val authRepository: AuthRepository,
) : ViewModel(), ContainerHost<SignInViewModel.State, SignInViewModel.SideEffect> {
    override val container = container<State, SideEffect>(State(buttonState = ButtonState.Success))

    private val _inputState = MutableStateFlow(SignInInputState())
    val input = _inputState.asStateFlow()

    fun inputLoginChanged(login: TextFieldValue) {
        _inputState.value = _inputState.value.copy(login = login)
    }

    fun inputPasswordChanged(password: TextFieldValue) {
        _inputState.value = _inputState.value.copy(password = password)
    }

    fun signIn() = intent {
        authRepository
            .login(
                login = input.value.login.text,
                password = input.value.password.text,
            )
            .onLoading {
                reduce { State(buttonState = ButtonState.Loading) }
            }
            .onSuccess {
                Log.d("Auth", "Success. Token = ${it.token}")

                reduce { State(buttonState = ButtonState.Success) }
                postSideEffect(SideEffect.UserLogin(token = it.token))
            }
            .onError {
                Log.d("Auth", "Error ${it.message}")

                reduce { State(buttonState = ButtonState.Error) }
            }
            .collect()
    }

    class State(val buttonState: ButtonState)

    sealed interface SideEffect {
        data class UserLogin(val token: String) : SideEffect
    }
}