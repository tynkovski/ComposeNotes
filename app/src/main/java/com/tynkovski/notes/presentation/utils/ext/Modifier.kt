package com.tynkovski.notes.presentation.utils.ext

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.union
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.tynkovski.notes.presentation.utils.ShadowModifier

inline fun Modifier.chose(
    value: Boolean,
    block: Modifier.() -> Modifier,
    another: Modifier.() -> Modifier
): Modifier = if (value) block.invoke(this) else another.invoke(this)

inline fun Modifier.ifTrue(
    value: Boolean,
    block: Modifier.() -> Modifier,
): Modifier = if (value) block.invoke(this) else this

inline fun Modifier.ifFalse(
    value: Boolean,
    block: Modifier.() -> Modifier,
): Modifier = if (value.not()) block.invoke(this) else this

fun Modifier.clearingFocusClickable(): Modifier = composed {
    val focusManager = LocalFocusManager.current
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = focusManager::clearFocus
    )
}

fun Modifier.clearingFocusClickable(onClick: () -> Unit): Modifier = composed {
    val focusManager = LocalFocusManager.current
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = {
            focusManager.clearFocus()
            onClick()
        }
    )
}

fun Modifier.horizontalNavigationBarsWithCutoutPadding(): Modifier = composed {
    padding(
        WindowInsets.horizontalNavigationBars
            .union(WindowInsets.horizontalCutout).asPaddingValues()
    )
}

fun Modifier.horizontalCutoutPadding(): Modifier = composed {
    padding(WindowInsets.horizontalCutout.asPaddingValues())
}

//fun Modifier.verticalNavigationBarsPadding(): Modifier = composed {
//    padding(WindowInsets.verticalNavigationBars.asPaddingValues())
//}
//
//fun Modifier.horizontalNavigationBarsPadding(): Modifier = composed {
//    padding(WindowInsets.horizontalNavigationBars.asPaddingValues())
//}
//
//fun Modifier.horizontalStatusBarsPadding(): Modifier = composed {
//    padding(WindowInsets.horizontalStatusBars.asPaddingValues())
//}

fun Modifier.shadow(
    shadow: Shadow,
    shape: Shape = RectangleShape,
) = this.then(ShadowModifier(shadow, shape))

fun Modifier.spacePageHorizontal() = this.then(padding(horizontal = 16.dp))

fun Modifier.spacePageVertical() = this.then(padding(vertical = 12.dp))
