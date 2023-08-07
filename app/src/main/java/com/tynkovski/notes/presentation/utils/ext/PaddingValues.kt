package com.tynkovski.notes.presentation.utils.ext

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import com.tynkovski.notes.presentation.theme.padding.appPaddings

@Composable
operator fun PaddingValues.plus(other: PaddingValues): PaddingValues {
    val direction = LocalLayoutDirection.current
    return PaddingValues(
        top = calculateTopPadding() + other.calculateTopPadding(),
        start = calculateStartPadding(direction) + other.calculateStartPadding(direction),
        end = calculateEndPadding(direction) + other.calculateEndPadding(direction),
        bottom = calculateBottomPadding() + other.calculateBottomPadding(),
    )
}

fun PaddingValues.pageHorizontal() = PaddingValues(horizontal = 16.dp)

fun PaddingValues.pageVertical() = PaddingValues(vertical = 12.dp)

fun PaddingValues.itemPaddings() = PaddingValues(horizontal = 16.dp, vertical = 4.dp)