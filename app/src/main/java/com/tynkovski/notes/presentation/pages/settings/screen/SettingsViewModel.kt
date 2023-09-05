package com.tynkovski.notes.presentation.pages.settings.screen

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class SettingsViewModel : ViewModel(),
    ContainerHost<SettingsViewModel.State, SettingsViewModel.SideEffect> {

    override val container = container<State, SideEffect>(State(loading = false))

    data class State(
        val loading: Boolean
    )

    interface SideEffect {

    }
}