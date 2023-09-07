package com.tynkovski.notes.presentation.pages.notes.compontents

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.RichText
import com.halilibo.richtext.ui.RichTextStyle
import com.tynkovski.notes.domain.models.Note
import com.tynkovski.notes.presentation.components.shimmer.DefaultShimmer
import com.tynkovski.notes.presentation.theme.NotesTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteHorizontal(
    selected: Boolean,
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    note: Note,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    val backgroundColor = animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.outlineVariant else Color(0xFFFBFDFA),
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
        TopCard(
            modifier = Modifier.fillMaxWidth(),
            title = note.title ?: "",
            color = note.color ?: -1
        )

        RichText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            Markdown(content = note.text)
        }
    }
}

@Composable
fun NoteShimmerHorizontal(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
) = DefaultShimmer(
    modifier = modifier,
    color = MaterialTheme.colorScheme.secondary,
    shape = shape
)

@Composable
private fun ColumnScope.TopCard(
    modifier: Modifier,
    title: String,
    color: Long
) {
    if (color > 0L) {
        Box(
            modifier = modifier
                .heightIn(min = 30.dp)
                .background(Color(color)),
            contentAlignment = Alignment.CenterStart
        ) {
            if (title.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    style = MaterialTheme.typography.titleMedium,
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black
                )
            }
        }
    } else {
        if (title.isNotEmpty()) {
            Text(
                modifier = modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                style = MaterialTheme.typography.titleMedium,
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black
            )
        }
    }
    if (title.isNotEmpty() || color > 0L) {
        Divider()
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
                title = "Test",
                text = "* first\n* second\n   * third",
                color = -1L,
                createdAt = -1L,
                updatedAt = -1L
            ),
            onClick = {},
            onLongClick = {},
        )
    }
}