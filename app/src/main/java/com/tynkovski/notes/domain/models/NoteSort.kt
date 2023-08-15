package com.tynkovski.notes.domain.models

sealed class NoteSort(
    private val isAscending: Boolean
) {
    override fun toString(): String {
        return when (this) {
            is ByTitle -> "TITLE_"
            is ByDate -> "DATE_"
            is ByText -> "TEXT_"
        } + if (isAscending) "ASC" else "DESC"
    }

    class ByDate(isAscending: Boolean) : NoteSort(isAscending)
    class ByTitle(isAscending: Boolean) : NoteSort(isAscending)
    class ByText(isAscending: Boolean) : NoteSort(isAscending)
}