package com.tynkovski.notes.presentation.components.helpers

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisableMinimumTouch(
    enabled: Boolean = true,
    block: @Composable () -> Unit,
) = if (enabled) {
    CompositionLocalProvider(
        LocalMinimumInteractiveComponentEnforcement provides false,
    ) {
        block()
    }
} else {
    block()
}