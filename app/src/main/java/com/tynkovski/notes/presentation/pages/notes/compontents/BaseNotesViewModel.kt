package com.tynkovski.notes.presentation.pages.notes.compontents

import androidx.lifecycle.ViewModel
import com.tynkovski.notes.data.remote.models.ErrorException
import com.tynkovski.notes.domain.models.Note
import com.tynkovski.notes.domain.models.NoteSort
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

abstract class BaseNotesViewModel : ViewModel(),
    ContainerHost<BaseNotesViewModel.State, BaseNotesViewModel.SideEffect> {
    protected val defaultSort = NoteSort.ByDate(false)

    final override val container = container<State, SideEffect>(
        initialState = State.Loading(defaultSort)
    ) { getNotes() }

    abstract fun getNotes(): Job

    abstract fun selectNote(noteId: String): Job

    abstract fun cancelSelect(): Job

    abstract fun deleteSelectedNotes(): Job

    sealed interface State {
        val sort: NoteSort

        data class Loading(
            override val sort: NoteSort
        ) : State

        data class Success(
            override val sort: NoteSort,
            val notes: List<Note>,
            val selectedNotes: Set<String>
        ) : State

        data class Error(
            override val sort: NoteSort,
            val error: ErrorException
        ) : State
    }

    interface SideEffect {
        object HideDialog : SideEffect
    }
}