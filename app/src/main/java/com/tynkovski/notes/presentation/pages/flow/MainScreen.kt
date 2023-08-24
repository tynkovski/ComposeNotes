package com.tynkovski.notes.presentation.pages.flow

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.union
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tynkovski.notes.R
import com.tynkovski.notes.presentation.navigation.DrawerScreen
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
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    val (selectedItem, selectedItemChanged) = remember { mutableStateOf(0) }

    val items = listOf(
        DrawerScreen(
            name = stringResource(R.string.notes_title),
            icon = ImageVector.vectorResource(R.drawable.ic_note)
        ),
        DrawerScreen(
            name = stringResource(R.string.settings_title),
            icon = ImageVector.vectorResource(R.drawable.ic_settings)
        )
    )

    ModalNavigationDrawer(
        modifier = modifierMaxSize,
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.padding(end = 64.dp),
                windowInsets = WindowInsets.statusBars
                    .union(WindowInsets.horizontalCutout)
                    .union(WindowInsets.horizontalNavigationBars)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.titleLarge,
                        text = stringResource(R.string.modal_navigation_title),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.bodyLarge,
                        text = stringResource(R.string.modal_navigation_description),
                        textAlign = TextAlign.Center
                    )
                }

                items.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                        selected = index == selectedItem,
                        icon = {
                            Icon(imageVector = item.icon, contentDescription = null)
                        },
                        label = {
                            Text(item.name)
                        },
                        onClick = {
                            scope.launch { drawerState.close() }
                            selectedItemChanged(index)
                        }
                    )
                }
            }
        }
    ) {
        Scaffold(
            modifier = modifierMaxSize,
            topBar = {
                TopAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    title = {
                        Text(
                            modifier = Modifier,
                            text = items[selectedItem].name
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { scope.launch { drawerState.open() } }
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_menu),
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            val paddingModifier = modifierMaxSize.padding(paddingValues)
            when (selectedItem) {
                0 -> {

                }
                1 -> SettingsScreen(
                    modifier = paddingModifier,
                    logout = logout
                )
            }
        }
    }

//    val navController = rememberNavController()
//    Scaffold(
//        modifier = modifierMaxSize,
//        contentWindowInsets = WindowInsets.empty,
//    ) { paddingValues ->
//        val modifierWithPadding = modifierMaxSize.padding(paddingValues)
//
//        NavHost(
//            navController = navController,
//            startDestination = NotesScreen.route,
//            modifier = modifierMaxSize
//        ) {
//            composable(NotesScreen.route) {
//                NotesScreen(
//                    controller = navController,
//                    modifier = modifierWithPadding,
//                )
//            }
//
//            composable(SettingsScreen.route) {
//                SettingsScreen(
//                    controller = navController,
//                    modifier = modifierWithPadding,
//                    logout = logout
//                )
//            }
//
//            composable(SearchScreen.route) {
//                SearchScreen(
//                    controller = navController,
//                    modifier = modifierWithPadding,
//                )
//            }
//
//            composable(
//                DetailNoteScreen.route,
//                arguments = listOf(DetailNoteScreen.argument)
//            ) { backStackEntry ->
//                val noteId = backStackEntry.arguments
//                    ?.getString(DetailNoteScreen.argument.name)
//                    ?: error("invalid ${DetailNoteScreen.argument.name}")
//
//                DetailNoteScreen(
//                    controller = navController,
//                    modifier = modifierWithPadding,
//                    noteId = noteId
//                )
//            }
//        }
//    }
}
