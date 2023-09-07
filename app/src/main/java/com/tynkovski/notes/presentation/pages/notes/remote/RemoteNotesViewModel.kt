package com.tynkovski.notes.presentation.pages.notes.remote

import androidx.lifecycle.ViewModel
import com.tynkovski.notes.data.remote.models.ErrorException
import com.tynkovski.notes.data.remote.models.onError
import com.tynkovski.notes.data.remote.models.onLoading
import com.tynkovski.notes.data.remote.models.onSuccess
import com.tynkovski.notes.data.remote.repositories.NoteRepository
import com.tynkovski.notes.domain.models.Note
import com.tynkovski.notes.domain.models.NoteSort
import com.tynkovski.notes.presentation.pages.notes.compontents.BaseNotesViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class RemoteNotesViewModel(
    private val notesRepository: NoteRepository
) : BaseNotesViewModel() {
    override fun getNotes() = intent {
        val sort = (state as? State.Success)?.sort ?: defaultSort
        notesRepository
            .getNotes(sort)
            .onLoading {
                reduce { State.Loading(sort = sort) }
            }
            .onSuccess {
                reduce {
                    State.Success(sort = sort, notes = it.notes, selectedNotes = setOf())
                }
            }
            .onError {
                reduce { State.Error(sort = sort, error = it) }
            }
            .collect()
    }

    override fun selectNote(noteId: String) = intent {
        val oldState = (state as? State.Success) ?: return@intent
        reduce {
            if (oldState.selectedNotes.contains(noteId)) {
                oldState.copy(selectedNotes = oldState.selectedNotes - noteId)
            } else {
                oldState.copy(selectedNotes = oldState.selectedNotes + noteId)
            }
        }
    }

    override fun cancelSelect() = intent {
        val oldState = (state as? State.Success) ?: return@intent

        reduce { oldState.copy(selectedNotes = setOf()) }
    }

    override fun deleteSelectedNotes()= intent {
        val oldState = (state as? State.Success) ?: return@intent

        notesRepository
            .deleteNotes(oldState.selectedNotes.toList())
            .onLoading {
                reduce { State.Loading(oldState.sort) }
                postSideEffect(SideEffect.HideDialog)
            }
            .onSuccess {
                val notes = oldState.notes.filter { it.id !in oldState.selectedNotes }
                reduce {
                    oldState.copy(
                        notes = notes,
                        selectedNotes = setOf()
                    )
                }
                postSideEffect(SideEffect.HideDialog)
            }
            .onError {
                reduce { State.Error(sort = oldState.sort, error = it) }
                postSideEffect(SideEffect.HideDialog)
            }
            .collect()
    }
}