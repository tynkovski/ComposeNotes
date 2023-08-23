package com.tynkovski.notes.presentation.pages.flow

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tynkovski.notes.presentation.navigation.DetailNoteScreen
import com.tynkovski.notes.presentation.navigation.NotesScreen
import com.tynkovski.notes.presentation.navigation.SearchScreen
import com.tynkovski.notes.presentation.navigation.SettingsScreen
import com.tynkovski.notes.presentation.pages.detailNote.screen.DetailNoteScreen
import com.tynkovski.notes.presentation.pages.notes.screen.NotesScreen
import com.tynkovski.notes.presentation.pages.search.screen.SearchScreen
import com.tynkovski.notes.presentation.pages.settings.screen.SettingsScreen
import com.tynkovski.notes.presentation.utils.ext.empty

// region Reusable
private val modifierMaxSize = Modifier.fillMaxSize()
// endregion

@Composable
fun MainScreen(
    logout: () -> Unit
) {
    val navController = rememberNavController()

    Scaffold(
        modifier = modifierMaxSize,
        contentWindowInsets = WindowInsets.empty,
    ) { paddingValues ->
        val modifierWithPadding = modifierMaxSize.padding(paddingValues)

        NavHost(
            navController = navController,
            startDestination = NotesScreen.route,
            modifier = modifierMaxSize
        ) {
            composable(NotesScreen.route) {
                NotesScreen(
                    controller = navController,
                    modifier = modifierWithPadding,
                )
            }

            composable(SettingsScreen.route) {
                SettingsScreen(
                    controller = navController,
                    modifier = modifierWithPadding,
                    logout = logout
                )
            }

            composable(SearchScreen.route) {
                SearchScreen(
                    controller = navController,
                    modifier = modifierWithPadding,
                )
            }

            composable(
                DetailNoteScreen.route,
                arguments = listOf(DetailNoteScreen.argument)
            ) { backStackEntry ->
                val noteId = backStackEntry.arguments
                    ?.getString(DetailNoteScreen.argument.name)
                    ?: error("invalid ${DetailNoteScreen.argument.name}")

                DetailNoteScreen(
                    controller = navController,
                    modifier = modifierWithPadding,
                    noteId = noteId
                )
            }
        }
    }
}
