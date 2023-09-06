package com.tynkovski.notes.presentation.pages.notes.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tynkovski.notes.R
import com.tynkovski.notes.domain.models.Note
import com.tynkovski.notes.presentation.navigation.DetailNoteScreen
import com.tynkovski.notes.presentation.navigation.NewNoteScreen
import com.tynkovski.notes.presentation.pages.notes.compontents.NoteHorizontal
import com.tynkovski.notes.presentation.utils.ext.plus
import com.tynkovski.notes.presentation.utils.ext.randomString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import org.orbitmvi.orbit.compose.collectAsState

// region Reusable
private val modifierMaxSize = Modifier.fillMaxSize()
// endregion

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    controller: NavController,
    coroutineScope: CoroutineScope,
    drawerState: DrawerState,
    modifier: Modifier
) {
    val viewModel = getViewModel<NotesViewModel>()
    val state by viewModel.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    IconButton(
                        onClick = { coroutineScope.launch { drawerState.open() } }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_menu),
                            contentDescription = stringResource(id = R.string.menu)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { controller.navigate(route = NewNoteScreen.route) },
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_add),
                    contentDescription = stringResource(R.string.add)
                )
            }
        }
    ) { paddingValues ->
        when (val currentState = state) {
            is NotesViewModel.State.Error -> Error(
                modifier = modifierMaxSize.padding(paddingValues),
                state = currentState
            )

            is NotesViewModel.State.Loading -> Loading(
                modifier = modifierMaxSize.padding(paddingValues),
                state = currentState
            )

            is NotesViewModel.State.Success -> if (currentState.remoteNotes.isEmpty()) Empty(
                modifier = modifierMaxSize.padding(paddingValues)
            ) else Success(
                modifier = modifierMaxSize.padding(paddingValues),
                controller = controller,
                state = currentState
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Success(
    modifier: Modifier,
    controller: NavController,
    state: NotesViewModel.State.Success
) = LazyVerticalStaggeredGrid(
    modifier = modifier,
    columns = StaggeredGridCells.Fixed(2),
    verticalItemSpacing = 8.dp,
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    contentPadding = PaddingValues(horizontal = 16.dp) + PaddingValues(bottom = 86.dp),
) {
    items(
        items = state.remoteNotes,
        key = { it.id }
    ) {
        NoteHorizontal(
            modifier = Modifier.fillMaxWidth(),
            note = it,
            onClick = { controller.navigate(route = DetailNoteScreen.route(it.id)) },
            onLongClick = {}
        )
    }
}

@Composable
private fun Loading(
    modifier: Modifier,
    state: NotesViewModel.State.Loading
) = Box(
    modifier = modifier,
    contentAlignment = Alignment.Center
) {
    CircularProgressIndicator()
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
private fun Error(
    modifier: Modifier,
    state: NotesViewModel.State.Error
) {

}