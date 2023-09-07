package com.tynkovski.notes.presentation.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

// region Reusable
private val defaultPaddings = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
// endregion

@Composable
fun Dialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    shape: Shape = MaterialTheme.shapes.medium,
    contentPadding: PaddingValues = defaultPaddings,
    properties: DialogProperties = DialogProperties(),
    alignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    content: @Composable ColumnScope.() -> Unit,
) = if (visible) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
        content = {
            Column(
                modifier = Modifier
                    .background(backgroundColor, shape)
                    .padding(contentPadding),
                horizontalAlignment = alignment
            ) {
                content()
            }
        }
    )
} else Unit
