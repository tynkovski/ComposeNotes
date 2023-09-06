package com.tynkovski.notes.presentation.pages.notes.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.tynkovski.notes.data.remote.models.ErrorException
import com.tynkovski.notes.data.remote.models.onError
import com.tynkovski.notes.data.remote.models.onLoading
import com.tynkovski.notes.data.remote.models.onSuccess
import com.tynkovski.notes.data.remote.repositories.NoteRepository
import com.tynkovski.notes.domain.models.Note
import com.tynkovski.notes.domain.models.NoteSort
import kotlinx.coroutines.flow.collect
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class NotesViewModel(
    private val notesRepository: NoteRepository
) : ViewModel(),
    ContainerHost<NotesViewModel.State, NotesViewModel.SideEffect> {

    private val defaultSort = NoteSort.ByDate(false)

    override val container = container<State, SideEffect>(
        initialState = State.Loading(defaultSort) // todo get from holder
    ) {
        getNotes()
    }

    private fun getNotes() = intent {
        val sort = (state as? State.Success)?.sort ?: defaultSort
        notesRepository
            .getNotes(sort)
            .onLoading {
                Log.d("NotesViewModel State", "Loading")
                reduce { State.Loading(sort = sort) }
            }
            .onSuccess {
                Log.d("NotesViewModel State", "Success")
                reduce { State.Success(sort = sort, remoteNotes = it.notes) }
            }
            .onError {
                Log.d("NotesViewModel State", "Error")
                reduce { State.Error(sort = sort, error = it) }
            }
            .collect()
    }

    sealed interface State {
        val sort: NoteSort

        data class Loading(
            override val sort: NoteSort
        ) : State

        data class Success(
            override val sort: NoteSort,
            val remoteNotes: List<Note>
        ) : State

        data class Error(
            override val sort: NoteSort,
            val error: ErrorException
        ) : State
    }

    interface SideEffect
}