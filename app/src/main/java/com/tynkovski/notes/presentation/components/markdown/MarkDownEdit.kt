package com.tynkovski.notes.presentation.components.markdown

import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import com.tynkovski.notes.presentation.utils.ext.color
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun MarkDownEdit(
    edit: Boolean,
    text: String,
    textChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit = {}
) = if (edit) {
    BasicTextField(
        modifier = modifier,
        value = text,
        textStyle = textStyle.color(color = textColor),
        onValueChange = textChanged
    )
} else {
    MarkdownText(
        modifier = modifier.clickable(onClick = onClick),
        markdown = text,
        style = textStyle,
        color = textColor
    )
}