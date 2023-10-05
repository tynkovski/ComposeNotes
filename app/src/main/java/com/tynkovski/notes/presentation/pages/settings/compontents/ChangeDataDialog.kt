package com.tynkovski.notes.presentation.pages.settings.compontents

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.tynkovski.notes.R
import com.tynkovski.notes.presentation.components.button.ButtonState
import com.tynkovski.notes.presentation.components.button.DefaultButton
import com.tynkovski.notes.presentation.components.dialog.Dialog

@Composable
fun ChangeDataDialog(
    visible: Boolean,
    data: String,
    dataChanged: (String) -> Unit,
    onDismiss: () -> Unit
) = Dialog(
    visible = visible,
    onDismissRequest = onDismiss
) {
    val (newValue, newValueChanged) = remember {
        mutableStateOf(data)
    }

    BasicTextField(
        modifier = Modifier.fillMaxWidth(),
        value = newValue,
        onValueChange = newValueChanged
    )

    Spacer(modifier = Modifier.height(8.dp))

    Row {
        DefaultButton(
            onClick = { dataChanged(newValue) },
            text = stringResource(R.string.profile_screen_edit_field),
            contentPadding = PaddingValues(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        DefaultButton(
            onClick = onDismiss,
            text = stringResource(R.string.note_title_delete_cancel),
            contentPadding = PaddingValues(horizontal = 16.dp)
        )
    }
}