package com.tynkovski.notes.presentation.pages.notes.compontents

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tynkovski.notes.domain.models.Note
import com.tynkovski.notes.presentation.theme.NotesTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteHorizontal(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    innerPadding: Dp = 12.dp,
    note: Note,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) = Card(
    modifier = modifier
        .clip(shape)
        .combinedClickable(onClick = onClick, onLongClick = onLongClick),
    shape = shape,
    elevation = CardDefaults.elevatedCardElevation(),
    colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.background,
    )
) {
    note.color?.let {
        Spacer(
            modifier = Modifier
                .height(30.dp)
                .fillMaxWidth()
                .background(Color(it))
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(innerPadding),
    ) {
        note.title?.let {
            Text(
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium,
                text = it,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))
        }

        Text(
            modifier = Modifier,
            style = MaterialTheme.typography.labelLarge,
            text = note.text,
            maxLines = 8,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteHorizontalPreview() {
    NotesTheme {
        NoteHorizontal(
            modifier = Modifier,
            note = Note(
                id = "intellegat",
                title = "Title Example Title Example Title Example Title Example Title Example",
                text = "Text example",
                color = 0xff_ff_00_00L,
                createdAt = -1L,
                updatedAt = -1L
            ),
            onClick = {},
            onLongClick = {},
        )
    }
}