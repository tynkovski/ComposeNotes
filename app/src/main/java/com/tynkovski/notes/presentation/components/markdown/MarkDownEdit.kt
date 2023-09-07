package com.tynkovski.notes.presentation.components.markdown

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.RichText
import com.tynkovski.notes.presentation.utils.ext.color

@Composable
fun MarkDownEdit(
    edit: Boolean,
    text: String,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    textChanged: (String) -> Unit,
    textStyle: TextStyle = TextStyle.Default,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit = {}
) = if (edit) {
    BasicTextField(
        modifier = modifier.padding(contentPadding),
        value = text,
        textStyle = textStyle.color(color = textColor),
        onValueChange = textChanged,

    )
} else {
    RichText(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(contentPadding),
    ) {
        Markdown(content = text)
    }
}