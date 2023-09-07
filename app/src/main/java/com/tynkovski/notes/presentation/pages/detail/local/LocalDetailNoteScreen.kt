package com.tynkovski.notes.presentation.pages.detail.local

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.tynkovski.notes.presentation.pages.detail.compontents.BaseDetailScreen
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun LocalDetailNoteScreen(
    controller: NavController,
    modifier: Modifier,
    noteId: String,
) {
    val viewModel = getViewModel<LocalDetailNoteViewModel>(
        parameters = { parametersOf(noteId) }
    )
    BaseDetailScreen(
        controller = controller,
        viewModel = viewModel,
        modifier = modifier
    )
}