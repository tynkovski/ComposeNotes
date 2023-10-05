package com.tynkovski.notes.presentation.components.iconButton

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tynkovski.notes.presentation.components.helpers.DisableMinimumTouch

@Composable
fun DefaultIconButton(
    imageVector: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.inversePrimary,
    iconTint: Color = MaterialTheme.colorScheme.onBackground,
    shape: Shape = CircleShape,
    ignoreMinimumTouch: Boolean = false,
    imageOffset: Dp = 0.dp,
    contentDescription: String? = null
) = DisableMinimumTouch(ignoreMinimumTouch) {
    FilledTonalIconButton(
        modifier = modifier,
        onClick = onClick,
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = backgroundColor,
        ),
        shape = shape,
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .offset(x = imageOffset),
            imageVector = imageVector,
            tint = iconTint,
            contentDescription = contentDescription
        )
    }
}
