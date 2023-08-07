package com.tynkovski.notes.presentation.pages.authorization.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.tynkovski.notes.R
import com.tynkovski.notes.presentation.components.iconButton.DefaultIconButton
import com.tynkovski.notes.presentation.utils.ext.clearingFocusClickable

// region Reusable
private val widthModifier = Modifier.fillMaxSize()
// endregion

@Composable
fun SignBase(
    modifier: Modifier,
    showIcon: Boolean,
    backClick: () -> Unit,
    content: @Composable ColumnScope. () -> Unit,
) = Scaffold(
    modifier = modifier,
    contentWindowInsets = WindowInsets.navigationBars
        .union(WindowInsets.statusBars)
        .union(WindowInsets.displayCutout)
        .union(WindowInsets.ime)
) { paddingValues ->
    Box(
        modifier = widthModifier.padding(paddingValues)
    ) {
        Column(
            modifier = widthModifier
                .verticalScroll(rememberScrollState())
                .clearingFocusClickable(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content.invoke(this@Column)
        }

        if (showIcon) {
            DefaultIconButton(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_back),
                onClick = backClick,
                backgroundColor = Color.Transparent,
            )
        }
    }
}