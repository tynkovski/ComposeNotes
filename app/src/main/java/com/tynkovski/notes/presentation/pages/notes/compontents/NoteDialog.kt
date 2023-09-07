package com.tynkovski.notes.presentation.pages.notes.compontents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tynkovski.notes.R
import com.tynkovski.notes.presentation.components.button.DefaultButton
import com.tynkovski.notes.presentation.components.dialog.Dialog

@Composable
fun NotesDialog(
    visible: Boolean,
    deleteNotes: () -> Unit,
    onDismissRequest: () -> Unit
) = Dialog(
    visible = visible,
    onDismissRequest = onDismissRequest
) {
    Text(
        modifier = Modifier,
        style = MaterialTheme.typography.titleMedium,
        text = stringResource(R.string.note_title_delete),
    )

    Spacer(modifier = Modifier.height(8.dp))

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DefaultButton(
            onClick = deleteNotes,
            text = stringResource(R.string.note_title_delete_confirm)
        )
        DefaultButton(
            onClick = onDismissRequest,
            text = stringResource(R.string.note_title_delete_cancel)
        )
    }
}