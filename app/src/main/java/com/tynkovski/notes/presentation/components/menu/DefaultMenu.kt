package com.tynkovski.notes.presentation.components.menu

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties

@Composable
fun DefaultMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    properties: PopupProperties = PopupProperties(focusable = true),
    items: @Composable ColumnScope.() -> Unit,
) = DropdownMenu(
    modifier = modifier,
    expanded = expanded,
    offset = offset,
    properties = properties,
    onDismissRequest = onDismissRequest
) {
    items.invoke(this@DropdownMenu)
}

@Composable
fun DefaultMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    properties: PopupProperties = PopupProperties(focusable = true),
    items: List<@Composable ColumnScope.() -> Unit>,
) = DropdownMenu(
    modifier = modifier,
    expanded = expanded,
    offset = offset,
    properties = properties,
    onDismissRequest = onDismissRequest
) {
    items.forEach { it.invoke(this@DropdownMenu) }
}

@Composable
fun DefaultMenuItem(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    enabled: Boolean = true,
) {
    val leadingIconNew: (@Composable () -> Unit)? = leadingIcon?.let {
        @Composable {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = it,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = null
            )
        }
    }

    val trailingIconNew: (@Composable () -> Unit)? = trailingIcon?.let {
        @Composable {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = it,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = text
            )
        }
    }

    DropdownMenuItem(
        text = { Text(modifier = Modifier, text = text) },
        modifier = modifier,
        onClick = onClick,
        leadingIcon = leadingIconNew,
        trailingIcon = trailingIconNew,
        enabled = enabled,
    )
}