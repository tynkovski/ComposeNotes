package com.tynkovski.notes.presentation.pages.detail.remote

import com.tynkovski.notes.data.remote.models.onError
import com.tynkovski.notes.data.remote.models.onLoading
import com.tynkovski.notes.data.remote.models.onSuccess
import com.tynkovski.notes.data.remote.repositories.NoteRepository
import com.tynkovski.notes.presentation.pages.detail.compontents.BaseDetailViewModel
import kotlinx.coroutines.flow.collect
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

class RemoteDetailNoteViewModel(
    noteId: String,
    private val notesRepository: NoteRepository
) : BaseDetailViewModel(noteId) {

    override fun getNote() = intent {
        state.noteId?.let { noteId ->
            notesRepository.getNote(noteId)
                .onLoading {
                    reduce { State.Loading(noteId) }
                }.onSuccess {
                    reduce {
                        State.Success(
                            title = it.title ?: "",
                            text = it.text,
                            color = it.color ?: -1,
                            edit = false,
                            noteId = noteId
                        )
                    }
                }.onError {
                    reduce { State.Error(it, noteId) }
                }.collect()
        } ?: run {
            reduce {
                State.Success(
                    title = "",
                    text = "",
                    color = -1,
                    edit = true,
                    noteId = null
                )
            }
        }
    }

    override fun startEditNote() = intent {
        val oldState = (state as? State.Success) ?: return@intent
        reduce { oldState.copy(edit = true) }
    }

    override fun saveNote(
        title: String,
        text: String,
        color: Long
    ) = intent {
        val flow = state.noteId?.let { noteId ->
            notesRepository.updateNote(
                noteId = noteId,
                title = title,
                text = text,
                color = color
            )
        } ?: notesRepository.createNote(
            title = title,
            text = text,
            color = color
        )

        flow.onSuccess {
            reduce {
                State.Success(
                    title = title,
                    text = text,
                    color = color,
                    edit = false,
                    noteId = it.id
                )
            }
        }.onError {
            reduce { State.Error(it, null) }
        }.collect()
    }

    override fun deleteNote() = intent {
        state.noteId?.let { noteId ->
            notesRepository.deleteNote(noteId)
                .onSuccess {
                    postSideEffect(SideEffect.NavigateBack)
                }.collect()
        }
    }
}