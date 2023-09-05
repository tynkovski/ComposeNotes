package com.tynkovski.notes.presentation.pages.flow

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.union
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tynkovski.notes.R
import com.tynkovski.notes.presentation.navigation.DetailNoteScreen
import com.tynkovski.notes.presentation.navigation.DrawerNavigation
import com.tynkovski.notes.presentation.navigation.NotesScreen
import com.tynkovski.notes.presentation.navigation.SettingsScreen
import com.tynkovski.notes.presentation.pages.detailNote.screen.DetailNoteScreen
import com.tynkovski.notes.presentation.pages.notes.screen.NotesScreen
import com.tynkovski.notes.presentation.pages.settings.screen.SettingsScreen
import com.tynkovski.notes.presentation.utils.ext.horizontalCutout
import com.tynkovski.notes.presentation.utils.ext.horizontalNavigationBars
import kotlinx.coroutines.launch

// region Reusable
private val modifierMaxSize = Modifier.fillMaxSize()
// endregion

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    logout: () -> Unit
) {
    val scope = rememberCoroutineScope()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    ModalNavigationDrawer(
        modifier = modifierMaxSize,
        drawerState = drawerState,
        drawerContent = {
            val currentDestination = navBackStackEntry?.destination

            ModalDrawerSheet(
                modifier = Modifier.padding(end = 64.dp),
                windowInsets = WindowInsets.statusBars
                    .union(WindowInsets.horizontalCutout)
                    .union(WindowInsets.horizontalNavigationBars)
            ) {
                DrawerNavigation.drawerScreens.forEach { screen ->
                    val title = stringResource(screen.title)
                    val icon = ImageVector.vectorResource(screen.image)

                    NavigationDrawerItem(
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                        selected = currentDestination
                            ?.hierarchy
                            ?.any { it.route == screen.route } == true,
                        icon = { Icon(icon, title) },
                        label = { Text(title) },
                        onClick = {
                            scope.launch {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                                drawerState.close()
                            }
                        }
                    )
                }
            }
        }
    ) {
        val a by remember {
            derivedStateOf {
                DrawerNavigation.drawerScreens
                    .map { it.route }
                    .contains(navBackStackEntry?.destination?.route)
            }
        }

        Scaffold(
            modifier = modifierMaxSize,
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = NotesScreen.route,
                modifier = modifierMaxSize
            ) {
                val paddingModifier = modifierMaxSize.padding(paddingValues)

                composable(NotesScreen.route) {
                    NotesScreen(
                        controller = navController,
                        modifier = paddingModifier,
                    )
                }

                composable(SettingsScreen.route) {
                    SettingsScreen(
                        controller = navController,
                        modifier = paddingModifier,
                        logout = logout
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
                        modifier = paddingModifier,
                        noteId = noteId
                    )
                }
            }
        }
    }
}
