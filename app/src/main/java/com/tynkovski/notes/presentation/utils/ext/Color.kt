package com.tynkovski.notes.presentation.utils.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

fun Color.alpha(alpha: Float) = copy(alpha = alpha)
val Color.dimmed @Composable get() = alpha(0.06f)

val Color.colorOver get() = if (luminance() < 0.5f) Color.White else Color.Black

