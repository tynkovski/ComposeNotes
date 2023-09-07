package com.tynkovski.notes.presentation.pages.flow

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.union
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tynkovski.notes.R
import com.tynkovski.notes.presentation.navigation.DrawerNavigation
import com.tynkovski.notes.presentation.navigation.LocalDetailNoteScreen
import com.tynkovski.notes.presentation.navigation.LocalNotesScreen
import com.tynkovski.notes.presentation.navigation.NewLocalNoteScreen
import com.tynkovski.notes.presentation.navigation.NewRemoteNoteScreen
import com.tynkovski.notes.presentation.navigation.RemoteDetailNoteScreen
import com.tynkovski.notes.presentation.navigation.RemoteNotesScreen
import com.tynkovski.notes.presentation.navigation.SettingsScreen
import com.tynkovski.notes.presentation.pages.detail.local.LocalDetailNoteScreen
import com.tynkovski.notes.presentation.pages.detail.remote.RemoteDetailNoteScreen
import com.tynkovski.notes.presentation.pages.notes.local.LocalNotesScreen
import com.tynkovski.notes.presentation.pages.notes.remote.RemoteNotesScreen
import com.tynkovski.notes.presentation.pages.settings.screen.SettingsScreen
import com.tynkovski.notes.presentation.utils.ext.empty
import com.tynkovski.notes.presentation.utils.ext.horizontalCutout
import kotlinx.coroutines.launch

// region Reusable
private val modifierMaxSize = Modifier.fillMaxSize()
private val modifierWidth = Modifier.fillMaxWidth()
// endregion

@Composable
fun MainScreen(
    logout: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    ModalNavigationDrawer(
        modifier = modifierMaxSize,
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                modifier = Modifier.padding(end = 64.dp),
                currentDestination = navBackStackEntry?.destination,
                onItemClick = {
                    coroutineScope.launch {
                        navController.navigate(it.route) {
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
    ) {
        Scaffold(
            modifier = modifierMaxSize,
            contentWindowInsets = WindowInsets.empty
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = RemoteNotesScreen.route,
                modifier = modifierMaxSize
            ) {
                val paddingModifier = modifierMaxSize.padding(paddingValues)

                composable(RemoteNotesScreen.route) {
                    RemoteNotesScreen(
                        controller = navController,
                        modifier = paddingModifier,
                        coroutineScope = coroutineScope,
                        drawerState = drawerState
                    )
                }

                composable(LocalNotesScreen.route) {
                    LocalNotesScreen(
                        controller = navController,
                        modifier = paddingModifier,
                        coroutineScope = coroutineScope,
                        drawerState = drawerState
                    )
                }

                composable(SettingsScreen.route) {
                    SettingsScreen(
                        controller = navController,
                        modifier = paddingModifier,
                        coroutineScope = coroutineScope,
                        drawerState = drawerState,
                        logout = logout
                    )
                }

                composable(NewRemoteNoteScreen.route) {
                    RemoteDetailNoteScreen(
                        controller = navController,
                        modifier = paddingModifier,
                        noteId = ""
                    )
                }

                composable(NewLocalNoteScreen.route) {
                    LocalDetailNoteScreen(
                        controller = navController,
                        modifier = paddingModifier,
                        noteId = ""
                    )
                }

                composable(
                    RemoteDetailNoteScreen.route,
                    arguments = listOf(RemoteDetailNoteScreen.argument)
                ) { backStackEntry ->
                    val noteId = backStackEntry.arguments
                        ?.getString(RemoteDetailNoteScreen.argument.name)
                        ?: error("invalid ${RemoteDetailNoteScreen.argument.name}")

                    RemoteDetailNoteScreen(
                        controller = navController,
                        modifier = paddingModifier,
                        noteId = noteId
                    )
                }

                composable(
                    LocalDetailNoteScreen.route,
                    arguments = listOf(LocalDetailNoteScreen.argument)
                ) { backStackEntry ->
                    val noteId = backStackEntry.arguments
                        ?.getString(LocalDetailNoteScreen.argument.name)
                        ?: error("invalid ${LocalDetailNoteScreen.argument.name}")

                    LocalDetailNoteScreen(
                        controller = navController,
                        modifier = paddingModifier,
                        noteId = noteId
                    )
                }
            }
        }
    }
}

@Composable
fun DrawerContent(
    modifier: Modifier,
    currentDestination: NavDestination?,
    onItemClick: (screen: DrawerNavigation) -> Unit,
) = ModalDrawerSheet(
    modifier = modifier,
    windowInsets = WindowInsets.statusBars
        .union(WindowInsets.horizontalCutout)
        .union(WindowInsets.navigationBars)
) {
    Spacer(modifier = Modifier.height(24.dp))
    Text(
        modifier = modifierWidth,
        style = MaterialTheme.typography.displaySmall,
        text = stringResource(R.string.modal_navigation_title),
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        modifier = modifierWidth,
        style = MaterialTheme.typography.bodyLarge,
        text = stringResource(R.string.modal_navigation_description),
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(20.dp))

    Divider()

    Spacer(modifier = Modifier.height(24.dp))

    DrawerNavigation.drawerScreens.forEachIndexed { index, screen ->
        val title = stringResource(screen.title)
        val icon = ImageVector.vectorResource(screen.image)

        if (index == DrawerNavigation.drawerScreens.lastIndex) {
            Spacer(modifier = Modifier.weight(1f))
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(modifier = Modifier.height(2.dp))
        } else {
            Spacer(modifier = Modifier.height(4.dp))
        }

        NavigationDrawerItem(
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
            selected = currentDestination
                ?.hierarchy
                ?.any { it.route == screen.route } == true,
            icon = { Icon(icon, title) },
            label = { Text(title) },
            onClick = {
                onItemClick(screen)

            }
        )
    }
}