package com.tynkovski.notes.presentation.components.popup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

// region Reusable
private val defaultPaddings = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
// endregion

@Composable
fun Popup(
    visible: Boolean,
    onDismissRequest: (() -> Unit)? = null,
    alignment: Alignment = Alignment.Center,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    shape: Shape = MaterialTheme.shapes.medium,
    contentPadding: PaddingValues = defaultPaddings,
    offset: IntOffset = IntOffset(0, 0),
    properties: PopupProperties = PopupProperties(),
    content: @Composable () -> Unit
) = if (visible) {
    Popup(
        alignment = alignment,
        offset = offset,
        onDismissRequest = onDismissRequest,
        properties = properties,
        content = {
            Column(
                modifier = Modifier
                    .background(backgroundColor, shape)
                    .padding(contentPadding)
            ) {
                content()
            }
        }
    )
} else Unit