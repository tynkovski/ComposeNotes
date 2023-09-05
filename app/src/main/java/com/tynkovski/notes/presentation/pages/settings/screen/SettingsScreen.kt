package com.tynkovski.notes.presentation.pages.settings.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import org.koin.androidx.compose.getViewModel
import org.orbitmvi.orbit.compose.collectAsState

// region Reusable
private val modifierMaxSize = Modifier.fillMaxSize()
// endregion

@Composable
fun SettingsScreen(
    controller: NavController,
    modifier: Modifier,
    logout: () -> Unit,
) {
    val viewModel = getViewModel<SettingsViewModel>()
    val state by viewModel.collectAsState()
    Text(
        modifier = modifier,
        text = "settings"
    )
}