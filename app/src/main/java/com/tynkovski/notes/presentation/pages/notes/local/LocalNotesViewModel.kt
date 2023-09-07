package com.tynkovski.notes.presentation.pages.notes.local

import com.tynkovski.notes.presentation.pages.notes.compontents.BaseNotesViewModel
import org.orbitmvi.orbit.syntax.simple.intent

class LocalNotesViewModel(

) : BaseNotesViewModel() {
    override fun getNotes() = intent {
        TODO("Not yet implemented")
    }

    override fun selectNote(noteId: String) = intent {
        TODO("Not yet implemented")
    }

    override fun cancelSelect() = intent {
        TODO("Not yet implemented")
    }

    override fun deleteSelectedNotes() = intent {
        TODO("Not yet implemented")
    }
}