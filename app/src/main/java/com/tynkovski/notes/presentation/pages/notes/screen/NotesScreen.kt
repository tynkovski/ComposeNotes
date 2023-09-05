package com.tynkovski.notes.presentation.pages.notes.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tynkovski.notes.domain.models.Note
import com.tynkovski.notes.presentation.navigation.DetailNoteScreen
import com.tynkovski.notes.presentation.pages.notes.compontents.NoteHorizontal
import org.koin.androidx.compose.getViewModel
import org.orbitmvi.orbit.compose.collectAsState

// region Reusable
private val modifierMaxSize = Modifier.fillMaxSize()
// endregion

@Composable
fun NotesScreen(
    controller: NavController,
    modifier: Modifier
) {
    val viewModel = getViewModel<NotesViewModel>()
    val state by viewModel.collectAsState()

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(100) {
            NoteHorizontal(
                modifier = Modifier.fillMaxWidth(),
                note = Note(
                    id = "$it",
                    text = "Text",
                    title = "Title",
                    color = 0xff_ff_00_00L,
                    createdAt = -1,
                    updatedAt = -1
                ),
                onClick = {
                    controller.navigate(route = DetailNoteScreen.route("$it"))
                },
                onLongClick = {}
            )
        }
    }
}