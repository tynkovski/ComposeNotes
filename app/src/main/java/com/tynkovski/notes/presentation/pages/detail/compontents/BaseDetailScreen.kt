package com.tynkovski.notes.presentation.pages.detail.compontents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.union
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tynkovski.notes.R
import com.tynkovski.notes.presentation.components.button.DefaultButton
import com.tynkovski.notes.presentation.components.markdown.MarkDownEdit
import com.tynkovski.notes.presentation.utils.ext.horizontalCutout
import org.orbitmvi.orbit.compose.collectAsState

// region Reusable
private val modifierMaxSize = Modifier.fillMaxSize()
private val modifierWidth = Modifier.fillMaxWidth()
// endregion

@Composable
fun BaseDetailScreen(
    modifier: Modifier,
    controller: NavController,
    viewModel: BaseDetailViewModel
) {
    val state by viewModel.collectAsState()

    when (val newState = state) {
        is BaseDetailViewModel.State.Error -> Error(
            modifier = modifier,
            onRefresh = viewModel::getNote
        )

        BaseDetailViewModel.State.Loading -> Loading(
            modifier = modifier
        )

        is BaseDetailViewModel.State.Success -> Success(
            modifier = modifier,
            state = newState,
            onSaveNote = viewModel::saveNote,
            onStartEditNote = viewModel::startEditNote,
            onDeleteNote = viewModel::deleteNote,
            onBack = controller::popBackStack
        )
    }
}

@Composable
private fun Success(
    modifier: Modifier,
    state: BaseDetailViewModel.State.Success,
    onSaveNote: (
        title: String,
        text: String,
        color: Long
    ) -> Unit,
    onStartEditNote: () -> Unit,
    onDeleteNote: () -> Unit,
    onBack: () -> Unit
) {
    val (title, titleChanged) = remember(state) {
        mutableStateOf(state.title)
    }

    val (text, textChanged) = remember(state) {
        mutableStateOf(state.text)
    }

    val (color, colorChanged) = remember(state) {
        mutableStateOf(state.color)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            DetailTopBar(
                modifier = modifierWidth,
                title = title,
                titleChanged = titleChanged,
                onDeleteNote = onDeleteNote,
                onBack = onBack,
                edit = state.edit,
                onStartEditNote = onStartEditNote,
                color = color,
                colorChanged = colorChanged,
                onSaveNote = { onSaveNote(title, text, color) }
            )
        },
        contentWindowInsets = WindowInsets.horizontalCutout
            .union(WindowInsets.navigationBars)
    ) { paddingValues ->
        MarkDownEdit(
            modifier = modifierMaxSize
                .padding(paddingValues)
                .padding(16.dp),
            edit = state.edit,
            text = text,
            textChanged = textChanged,
            onClick = onStartEditNote
        )
    }
}

@Composable
private fun Loading(
    modifier: Modifier
) = Box(
    modifier = modifier,
    contentAlignment = Alignment.Center
) {
    CircularProgressIndicator()
}

@Composable
private fun Error(
    modifier: Modifier,
    onRefresh: () -> Unit
) = Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
) {
    Text(
        modifier = Modifier,
        style = MaterialTheme.typography.titleLarge,
        text = stringResource(id = R.string.note_error_title)
    )

    Spacer(modifier = Modifier.height(4.dp))

    DefaultButton(
        onClick = onRefresh,
        text = stringResource(id = R.string.note_button_refresh),
        contentPadding = PaddingValues(horizontal = 16.dp)
    )
}