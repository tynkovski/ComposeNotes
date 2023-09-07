package com.tynkovski.notes.presentation.pages.detail.compontents

import androidx.lifecycle.ViewModel
import com.tynkovski.notes.data.remote.models.ErrorException
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

abstract class BaseDetailViewModel(
    val noteId: String
) : ViewModel(),
    ContainerHost<BaseDetailViewModel.State, BaseDetailViewModel.SideEffect> {

    final override val container = container<State, SideEffect>(State.Loading) {
        getNote()
    }

    abstract fun getNote(): Job

    abstract fun startEditNote(): Job

    abstract fun saveNote(
        title: String,
        text: String,
        color: Long
    ): Job

    abstract fun deleteNote(): Job

    sealed interface State {

        object Loading : State

        data class Success(
            val title: String,
            val text: String,
            val color: Long,
            val edit: Boolean
        ) : State

        data class Error(
            val error: ErrorException
        ) : State
    }

    interface SideEffect {
        object NavigateBack : SideEffect

        data class LoadNote(
            val title: String,
            val text: String,
            val color: Long
        ) : SideEffect
    }
}

