package com.tynkovski.notes.presentation.pages.notes.local

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.tynkovski.notes.presentation.pages.notes.base.BaseNotesViewModel
import com.tynkovski.notes.presentation.pages.notes.remote.RemoteNotesViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.compose.getViewModel

@Composable
fun LocalNotesScreen(
    controller: NavController,
    coroutineScope: CoroutineScope,
    drawerState: DrawerState,
    modifier: Modifier,
    viewModel: BaseNotesViewModel = getViewModel<LocalNotesViewModel>(),
) {

}