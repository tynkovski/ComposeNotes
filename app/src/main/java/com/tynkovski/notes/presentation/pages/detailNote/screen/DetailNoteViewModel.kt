package com.tynkovski.notes.presentation.pages.detailNote.screen

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class DetailNoteViewModel(
    noteId: String, // check for empty
) : ViewModel(),
    ContainerHost<DetailNoteViewModel.State, DetailNoteViewModel.SideEffect> {

    override val container = container<State, SideEffect>(State(loading = false))

    data class State(
        val loading: Boolean
    )

    interface SideEffect {

    }
}