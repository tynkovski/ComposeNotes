package com.tynkovski.notes.presentation.pages.detailNote.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import org.orbitmvi.orbit.compose.collectAsState

// region Reusable
private val modifierMaxSize = Modifier.fillMaxSize()
// endregion

@Composable
fun DetailNoteScreen(
    controller: NavController,
    modifier: Modifier,
    noteId: String
) {
    val viewModel = getViewModel<DetailNoteViewModel>(
        parameters = { parametersOf(noteId) }
    )
    val state by viewModel.collectAsState()
}