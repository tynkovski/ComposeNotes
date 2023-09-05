package com.tynkovski.notes.presentation.pages.notes.screen

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class NotesViewModel : ViewModel(),
    ContainerHost<NotesViewModel.State, NotesViewModel.SideEffect> {

    override val container = container<State, SideEffect>(State(loading = false))

    data class State(
        val loading: Boolean
    )

    interface SideEffect {

    }
}