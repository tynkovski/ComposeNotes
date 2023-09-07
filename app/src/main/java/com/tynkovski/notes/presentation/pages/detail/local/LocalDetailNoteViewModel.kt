package com.tynkovski.notes.presentation.pages.detail.local

import com.tynkovski.notes.presentation.pages.detail.compontents.BaseDetailViewModel
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.syntax.simple.intent

class LocalDetailNoteViewModel(noteId: String) : BaseDetailViewModel(noteId) {

    override fun getNote() = intent {

    }

    override fun startEditNote(): Job {
        TODO("Not yet implemented")
    }

    override fun saveNote(
        title: String,
        text: String,
        color: Long
    ) = intent {
        TODO("Not yet implemented")
    }

    override fun deleteNote() = intent {
        TODO("Not yet implemented")
    }

}