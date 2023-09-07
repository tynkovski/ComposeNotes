package com.tynkovski.notes.presentation.pages.detail.remote

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.tynkovski.notes.presentation.pages.detail.compontents.BaseDetailScreen
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun RemoteDetailNoteScreen(
    controller: NavController,
    modifier: Modifier,
    noteId: String,
) {
    val viewModel = getViewModel<RemoteDetailNoteViewModel>(
        parameters = { parametersOf(noteId) }
    )
    BaseDetailScreen(
        controller = controller,
        viewModel = viewModel,
        modifier = modifier
    )
}