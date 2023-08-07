package com.tynkovski.notes.presentation.pages.flow

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.tynkovski.notes.presentation.utils.ext.empty

// region Reusable
private val modifierMaxSize = Modifier.fillMaxSize()
// endregion

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        modifier = modifierMaxSize,
        contentWindowInsets = WindowInsets.empty,
    ) { paddingValues ->
        val modifierWithPadding = modifierMaxSize.padding(paddingValues)
        NavHost(
            navController = navController,
            startDestination = "NotesScreen.route",// todo add start destination
            modifier = modifierMaxSize
        ) {
            // add screens
        }
    }
}
