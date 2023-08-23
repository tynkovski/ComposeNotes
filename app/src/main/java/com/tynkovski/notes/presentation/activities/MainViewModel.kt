package com.tynkovski.notes.presentation.activities

import androidx.lifecycle.ViewModel
import com.tynkovski.notes.data.local.holder.TokenHolder
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class MainViewModel(
    private val tokenHolder: TokenHolder
) : ViewModel(), ContainerHost<MainViewModel.State, MainViewModel.SideEffect> {
    override val container = container<State, SideEffect>(
        State(hasToken = tokenHolder.getToken().isNotEmpty())
    )

    fun login(token: String) = intent {
        tokenHolder.setToken(token)
        reduce { State(hasToken = true) }
    }

    fun logout() = intent {
        tokenHolder.logout()
        reduce { State(hasToken = false) }
    }

    data class State(
        val hasToken: Boolean
    )

    interface SideEffect
}