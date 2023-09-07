package com.tynkovski.notes.presentation.pages.notes.compontents

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.tynkovski.notes.presentation.utils.ext.ifTrue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteHorizontal(
    selected: Boolean,
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    innerPadding: Dp = 12.dp,
    note: Note,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    val backgroundColor = animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.outlineVariant else MaterialTheme.colorScheme.background,
        label = "background color animation"
    )

    Card(
        modifier = modifier
            .clip(shape)
            .combinedClickable(onClick = onClick, onLongClick = onLongClick)
            .background(shape = shape, color = backgroundColor.value),
        shape = shape,
        elevation = CardDefaults.elevatedCardElevation(),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor.value,
        ),
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

//        MarkdownText(
//            modifier = modifier,
//            markdown = note.text,
//            style = MaterialTheme.typography.labelLarge,
//            color = MaterialTheme.colorScheme.onBackground,
//            maxLines = 6
//        )

            Text(
                modifier = Modifier,
                style = MaterialTheme.typography.labelLarge,
                text = note.text,
                maxLines = 8,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteHorizontalPreview() {
    NotesTheme {
        NoteHorizontal(
            selected = false,
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

@Preview(showBackground = true)
@Composable
private fun NoteHorizontalSelectedPreview() {
    NotesTheme {
        NoteHorizontal(
            selected = true,
            modifier = Modifier,
            note = Note(
                id = "intellegat",
                title = "Title Example Title Example Title Example Title Example Title Example",
                text = "* first\n* second\n   * third",
                color = 0xff_ff_00_00L,
                createdAt = -1L,
                updatedAt = -1L
            ),
            onClick = {},
            onLongClick = {},
        )
    }
}