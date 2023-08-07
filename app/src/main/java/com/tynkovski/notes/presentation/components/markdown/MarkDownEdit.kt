package com.tynkovski.notes.presentation.components.markdown

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun MarkDownEdit(
    edit: Boolean,
    text: String,
    textChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default,
) = if (edit) {
    BasicTextField(
        modifier = modifier,
        value = text,
        textStyle = textStyle,
        onValueChange = textChanged
    )
} else {
    MarkdownText(
        modifier = modifier,
        markdown = text,
        style = textStyle,
    )
}