package com.tynkovski.notes.presentation.pages.detail.compontents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tynkovski.notes.R
import com.tynkovski.notes.presentation.components.button.ButtonState
import com.tynkovski.notes.presentation.components.button.DefaultButton
import com.tynkovski.notes.presentation.components.dialog.Dialog
import com.tynkovski.notes.presentation.utils.ext.ifTrue

private val colors = listOf(
    0xff_d98880,
    0xff_f1948a,
    0xff_c39bd3,
    0xff_bb8fce,
    0xff_7fb3d5,
    0xff_85c1e9,
    0xff_76d7c4,
    0xff_73c6b6,
    0xff_7dcea0,
    0xff_82e0aa,
    0xff_f7dc6f,
    0xff_f8c471,
    0xff_f0b27a,
    0xff_e59866
)

@Composable
fun ColorDialog(
    visible: Boolean,
    onDismiss: () -> Unit,
    currentColor: Long = -1L,
    onColorSelected: (color: Long) -> Unit,
) = Dialog(
    visible = visible,
    onDismissRequest = onDismiss
) {
    val (selectedColor, selectedColorChanged) = remember {
        mutableStateOf(currentColor)
    }

    val colorSize = 36.dp
    LazyVerticalGrid(
        columns = GridCells.Adaptive(colorSize),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(colors) {
            ColorElement(
                selected = selectedColor == it,
                modifier = Modifier.size(colorSize),
                color = Color(it),
                onClick = { selectedColorChanged(it) }
            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DefaultButton(
            modifier = Modifier.weight(1f),
            enabled = selectedColor > 0L,
            onClick = {
                onColorSelected(selectedColor)
                onDismiss()
            },
            text = stringResource(R.string.detail_note_set_color),
            contentPadding = PaddingValues(horizontal = 16.dp)
        )

        DefaultButton(
            modifier = Modifier.weight(1f),
            onClick = {
                onColorSelected(-1L)
                onDismiss()
            },
            text = stringResource(R.string.detail_note_button_delete_color),
            contentPadding = PaddingValues(horizontal = 16.dp),
            state = ButtonState.Error
        )

        DefaultButton(
            modifier = Modifier.weight(1f),
            onClick = onDismiss,
            text = stringResource(R.string.cancel),
            contentPadding = PaddingValues(horizontal = 16.dp)
        )
    }
}

@Composable
private fun ColorElement(
    selected: Boolean,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) = Spacer(
    modifier = modifier
        .clip(CircleShape)
        .background(color = color, shape = CircleShape)
        .ifTrue(selected) {
            border(width = 2.dp, color = MaterialTheme.colorScheme.primary, shape = CircleShape)
        }
        .clickable(onClick = onClick)
)