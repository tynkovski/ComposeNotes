package com.tynkovski.notes.presentation.pages.notes.remote

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.tynkovski.notes.presentation.pages.notes.compontents.BaseNotesScreen
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.compose.getViewModel

@Composable
fun RemoteNotesScreen(
    controller: NavController,
    coroutineScope: CoroutineScope,
    drawerState: DrawerState,
    modifier: Modifier,
) = BaseNotesScreen(
    viewModel = getViewModel<RemoteNotesViewModel>(),
    modifier = modifier,
    controller = controller,
    coroutineScope = coroutineScope,
    drawerState = drawerState
)