package com.tynkovski.notes.presentation.utils.ext

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp

val WindowInsets.Companion.empty: WindowInsets
    @Composable
    @NonRestartableComposable
    get() = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)

val WindowInsets.Companion.horizontalNavigationBars: WindowInsets
    @Composable
    @NonRestartableComposable
    get() = with(LocalDensity.current) {
        val direction = LocalLayoutDirection.current
        val left = WindowInsets.navigationBars.getLeft(this, direction)
        val right = WindowInsets.navigationBars.getRight(this, direction)
        return WindowInsets(left = left, right = right)
    }

val WindowInsets.Companion.horizontalStatusBars: WindowInsets
    @Composable
    @NonRestartableComposable
    get() = with(LocalDensity.current) {
        val direction = LocalLayoutDirection.current
        val left = WindowInsets.statusBars.getLeft(this, direction)
        val right = WindowInsets.statusBars.getRight(this, direction)
        return WindowInsets(left = left, right = right)
    }


val WindowInsets.Companion.verticalNavigationBars: WindowInsets
    @Composable
    @NonRestartableComposable
    get() = with(LocalDensity.current) {
        val bottom = WindowInsets.navigationBars.getBottom(this)
        return WindowInsets(bottom = bottom)
    }

val WindowInsets.Companion.horizontalCutout: WindowInsets
    @Composable
    @NonRestartableComposable
    get() {
        val density = LocalDensity.current
        val direction = LocalLayoutDirection.current
        val left = WindowInsets.displayCutout.getLeft(density, direction)
        val right = WindowInsets.displayCutout.getRight(density, direction)
        return WindowInsets(left = left, right = right)
    }

val WindowInsets.Companion.verticalCutout: WindowInsets
    @Composable
    @NonRestartableComposable
    get() = with(LocalDensity.current) {
        val top = WindowInsets.displayCutout.getTop(this)
        val bottom = WindowInsets.displayCutout.getBottom(this)
        return WindowInsets(top = top, bottom = bottom)
    }
