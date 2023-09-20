package com.tynkovski.notes.presentation.pages.notes.remote

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
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
import com.tynkovski.notes.presentation.pages.notes.base.BaseNotesViewModel
import com.tynkovski.notes.presentation.pages.notes.compontents.NoteHorizontal
import com.tynkovski.notes.presentation.pages.notes.compontents.NoteShimmerHorizontal
import com.tynkovski.notes.presentation.pages.notes.compontents.NotesDialog
import com.tynkovski.notes.presentation.pages.notes.compontents.NotesTopBar
import com.tynkovski.notes.presentation.utils.ext.horizontalCutout
import com.tynkovski.notes.presentation.utils.ext.plus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

// region Reusable
private val modifierMaxSize = Modifier.fillMaxSize()
private val modifierWidth = Modifier.fillMaxWidth()
// endregion

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterialApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun RemoteNotesScreen(
    controller: NavController,
    coroutineScope: CoroutineScope,
    drawerState: DrawerState,
    modifier: Modifier,
    viewModel: BaseNotesViewModel = getViewModel<RemoteNotesViewModel>(),
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
            AnimatedVisibility(
                visible = state is BaseNotesViewModel.State.Success,
                enter = scaleIn(),
                exit = scaleOut()
            ) {
                FloatingActionButton(
                    onClick = { controller.navigate(route = NewRemoteNoteScreen.route) },
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_add),
                        contentDescription = stringResource(R.string.add)
                    )
                }
            }
        },
        contentWindowInsets = WindowInsets.horizontalCutout.union(WindowInsets.navigationBars)
    ) { paddingValues ->
        val pullRefreshState = rememberPullRefreshState(
            refreshing = state is BaseNotesViewModel.State.Loading,
            onRefresh = viewModel::getNotes
        )

        val paddingModifier = modifierMaxSize.padding(paddingValues)

        Box(modifier = paddingModifier.pullRefresh(pullRefreshState)) {
            when (val currentState = state) {
                is BaseNotesViewModel.State.Error -> Error(
                    modifier = modifierMaxSize,
                    state = currentState,
                    onRefresh = viewModel::getNotes
                )

                is BaseNotesViewModel.State.Loading -> Loading(
                    modifier = modifierMaxSize,
                    state = currentState
                )

                is BaseNotesViewModel.State.Success -> if (currentState.notes.isEmpty()) Empty(
                    modifier = modifierMaxSize
                ) else Success(
                    modifier = modifierMaxSize,
                    controller = controller,
                    state = currentState,
                    selectNote = viewModel::selectNote,
                )
            }

            PullRefreshIndicator(
                refreshing = state is BaseNotesViewModel.State.Loading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                backgroundColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.secondary
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Loading(
    modifier: Modifier,
    state: BaseNotesViewModel.State.Loading
) = LazyVerticalStaggeredGrid(
    modifier = modifier,
    columns = StaggeredGridCells.Fixed(2),
    verticalItemSpacing = 8.dp,
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    contentPadding = PaddingValues(horizontal = 16.dp) + PaddingValues(bottom = 86.dp),
) {
    items(
        count = 16,
        key = { it }
    ) {
        NoteShimmerHorizontal(
            modifier = Modifier
                .fillMaxWidth()
                .height((56 * ((it % 4) + 1)).dp),
        )
    }
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
        text = stringResource(id = R.string.button_refresh),
        contentPadding = PaddingValues(horizontal = 16.dp)
    )
}