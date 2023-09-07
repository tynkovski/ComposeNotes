package com.tynkovski.notes.presentation.pages.notes.compontents

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tynkovski.notes.R
import com.tynkovski.notes.presentation.components.button.DefaultButton
import com.tynkovski.notes.presentation.navigation.NewRemoteNoteScreen
import com.tynkovski.notes.presentation.navigation.RemoteDetailNoteScreen
import com.tynkovski.notes.presentation.utils.ext.horizontalCutout
import com.tynkovski.notes.presentation.utils.ext.plus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

// region Reusable
private val modifierMaxSize = Modifier.fillMaxSize()
private val modifierWidth = Modifier.fillMaxWidth()
// endregion

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseNotesScreen(
    viewModel: BaseNotesViewModel,
    modifier: Modifier,
    controller: NavController,
    coroutineScope: CoroutineScope,
    drawerState: DrawerState
) {
    val state by viewModel.collectAsState()

    val (showDialog, showDialogChanged) = remember {
        mutableStateOf(false)
    }

    NotesDialog(
        visible = showDialog,
        deleteNotes = viewModel::deleteSelectedNotes,
        onDismissRequest = { showDialogChanged(false) },
    )

    viewModel.collectSideEffect {
        when (it) {
            is BaseNotesViewModel.SideEffect.HideDialog -> showDialogChanged(false)
        }
    }

    DisposableEffect(controller) {
        viewModel.getNotes()
        onDispose {}
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            NotesTopBar(
                modifier = modifierWidth,
                state = state,
                onDeleteSelectedNotes = { showDialogChanged(true) },
                onCancelSelect = viewModel::cancelSelect,
                onMenuClick = { coroutineScope.launch { drawerState.open() } }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { controller.navigate(route = NewRemoteNoteScreen.route) },
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_add),
                    contentDescription = stringResource(R.string.add)
                )
            }
        },
        contentWindowInsets = WindowInsets.horizontalCutout
            .union(WindowInsets.navigationBars)
    ) { paddingValues ->
        when (val currentState = state) {
            is BaseNotesViewModel.State.Error -> Error(
                modifier = modifierMaxSize.padding(paddingValues),
                state = currentState,
                onRefresh = viewModel::getNotes
            )

            is BaseNotesViewModel.State.Loading -> Loading(
                modifier = modifierMaxSize.padding(paddingValues),
                state = currentState
            )

            is BaseNotesViewModel.State.Success -> if (currentState.notes.isEmpty()) Empty(
                modifier = modifierMaxSize.padding(paddingValues)
            ) else Success(
                modifier = modifierMaxSize.padding(paddingValues),
                controller = controller,
                state = currentState,
                selectNote = viewModel::selectNote
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Success(
    modifier: Modifier,
    controller: NavController,
    state: BaseNotesViewModel.State.Success,
    selectNote: (id: String) -> Unit
) {
    val haptic = LocalHapticFeedback.current

    LazyVerticalStaggeredGrid(
        modifier = modifier,
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp) + PaddingValues(bottom = 86.dp),
    ) {
        items(
            items = state.notes,
            key = { it.id }
        ) {
            NoteHorizontal(
                modifier = Modifier.fillMaxWidth(),
                note = it,
                selected = it.id in state.selectedNotes,
                onClick = {
                    if (state.selectedNotes.isNotEmpty()) {
                        selectNote(it.id)
                    } else {
                        controller.navigate(route = RemoteDetailNoteScreen.route(it.id))
                    }
                },
                onLongClick = {
                    if (state.selectedNotes.isEmpty()) {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    }
                    selectNote(it.id)
                }
            )
        }
    }
}

@Composable
private fun Empty(
    modifier: Modifier
) = Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
) {
    Text(
        modifier = Modifier,
        style = MaterialTheme.typography.titleLarge,
        text = stringResource(id = R.string.notes_empty_title),
    )

    Spacer(modifier = Modifier.height(4.dp))

    Text(
        modifier = Modifier,
        style = MaterialTheme.typography.bodyLarge,
        text = stringResource(id = R.string.notes_empty_description),
    )
}

@Composable
private fun Loading(
    modifier: Modifier,
    state: BaseNotesViewModel.State.Loading
) = Box(
    modifier = modifier,
    contentAlignment = Alignment.Center
) {
    CircularProgressIndicator()
}

@Composable
private fun Error(
    modifier: Modifier,
    state: BaseNotesViewModel.State.Error,
    onRefresh: () -> Unit
) = Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
) {
    Text(
        modifier = Modifier,
        style = MaterialTheme.typography.titleLarge,
        text = stringResource(id = R.string.notes_error_title)
    )

    Spacer(modifier = Modifier.height(4.dp))

    DefaultButton(
        onClick = onRefresh,
        text = stringResource(id = R.string.note_button_refresh),
        contentPadding = PaddingValues(horizontal = 16.dp)
    )
}