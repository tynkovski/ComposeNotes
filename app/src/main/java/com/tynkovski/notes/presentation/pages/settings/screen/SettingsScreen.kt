package com.tynkovski.notes.presentation.pages.settings.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import com.tynkovski.notes.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import org.orbitmvi.orbit.compose.collectAsState

// region Reusable
private val modifierMaxSize = Modifier.fillMaxSize()
// endregion

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    controller: NavController,
    modifier: Modifier,
    coroutineScope: CoroutineScope,
    drawerState: DrawerState,
    logout: () -> Unit,
) {
    val viewModel = getViewModel<SettingsViewModel>()
    val state by viewModel.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    IconButton(
                        onClick = { coroutineScope.launch { drawerState.open() } }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_menu),
                            contentDescription = stringResource(id = R.string.menu)
                        )
                    }
                }
            )
        },
    ) { paddingValues ->
        Text(
            modifier = modifier.padding(paddingValues),
            text = "settings"
        )
    }
}