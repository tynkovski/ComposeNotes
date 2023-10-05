package com.tynkovski.notes.presentation.pages.settings.screen

import androidx.lifecycle.ViewModel
import com.tynkovski.notes.data.local.holders.TokenHolder
import com.tynkovski.notes.data.remote.models.ErrorException
import com.tynkovski.notes.data.remote.models.onError
import com.tynkovski.notes.data.remote.models.onLoading
import com.tynkovski.notes.data.remote.models.onSuccess
import com.tynkovski.notes.data.remote.repositories.UserRepository
import com.tynkovski.notes.domain.models.User
import kotlinx.coroutines.flow.collect
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class SettingsViewModel(
    private val tokenHolder: TokenHolder,
    private val userRepository: UserRepository
) : ViewModel(),
    ContainerHost<SettingsViewModel.State, SettingsViewModel.SideEffect> {

    fun logout() = intent {
        tokenHolder.logout()
        postSideEffect(SideEffect.Logout)
    }

    override val container = container<State, SideEffect>(State.Loading) {
        getUser()
    }

    fun getUser() = intent {
        userRepository.getUser()
            .onLoading {
                reduce { State.Loading }
            }.onSuccess {
                reduce { State.Success(it) }
            }.onError {
                reduce { State.Error(it) }
            }.collect()
    }

    sealed interface State {
        object Loading : State
        data class Success(val user: User) : State
        data class Error(val error: ErrorException) : State
    }

    interface SideEffect {
        object Logout : SideEffect
    }
}