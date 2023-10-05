package com.tynkovski.notes.presentation.pages.settings.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tynkovski.notes.R
import com.tynkovski.notes.presentation.components.button.ButtonState
import com.tynkovski.notes.presentation.components.button.DefaultButton
import com.tynkovski.notes.presentation.components.iconButton.DefaultIconButton
import com.tynkovski.notes.presentation.pages.settings.compontents.UserProfile
import com.tynkovski.notes.presentation.pages.settings.compontents.UserProfileError
import com.tynkovski.notes.presentation.pages.settings.compontents.UserProfileLoading
import com.tynkovski.notes.presentation.utils.ext.horizontalCutout
import com.tynkovski.notes.presentation.utils.ext.horizontalNavigationBars
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

// region Reusable
private val modifierMaxSize = Modifier.fillMaxSize()
// endregion

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
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

    viewModel.collectSideEffect {
        when (it) {
            is SettingsViewModel.SideEffect.Logout -> logout()
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    DefaultIconButton(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_menu),
                        onClick = { coroutineScope.launch { drawerState.open() } },
                        backgroundColor = Color.Transparent,
                        iconTint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = stringResource(R.string.menu)
                    )
                },
                title = {
                    Row(
                        modifier = modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = stringResource(R.string.settings_title),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                },
                windowInsets = WindowInsets.statusBars
                    .union(WindowInsets.horizontalCutout)
                    .union(WindowInsets.horizontalNavigationBars),
            )
        },
        contentWindowInsets = WindowInsets.horizontalCutout.union(WindowInsets.navigationBars)
    ) { paddingValues ->
        val paddingModifier = modifierMaxSize.padding(paddingValues)

        val pullRefreshState = rememberPullRefreshState(
            refreshing = state is SettingsViewModel.State.Loading,
            onRefresh = viewModel::getUser
        )

        Box(modifier = paddingModifier.pullRefresh(pullRefreshState)) {
            Column(modifier = modifierMaxSize.padding(horizontal = 16.dp)) {
                when (val newState = state) {
                    is SettingsViewModel.State.Error -> {
                        UserProfileError(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            error = newState.error,
                            onRefresh = viewModel::getUser
                        )
                    }

                    SettingsViewModel.State.Loading -> {
                        UserProfileLoading(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                        )
                    }

                    is SettingsViewModel.State.Success -> {
                        UserProfile(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            login = newState.user.login,
                            name = newState.user.name
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Spacer(modifier = Modifier.weight(1f))

                DefaultButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = viewModel::logout,
                    text = stringResource(R.string.logout),
                    leadingIcon = ImageVector.vectorResource(R.drawable.ic_logout),
                    state = ButtonState.Error
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            PullRefreshIndicator(
                refreshing = state is SettingsViewModel.State.Loading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                backgroundColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.secondary
            )
        }
    }
}