package com.tynkovski.notes.presentation.pages.notes.compontents

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.union
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tynkovski.notes.R
import com.tynkovski.notes.presentation.components.anim.AnimatedFade
import com.tynkovski.notes.presentation.components.anim.AnimatedScale
import com.tynkovski.notes.presentation.components.iconButton.DefaultIconButton
import com.tynkovski.notes.presentation.components.topBar.AnimatedSelectTopBar
import com.tynkovski.notes.presentation.utils.ext.horizontalCutout
import com.tynkovski.notes.presentation.utils.ext.horizontalNavigationBars

// region Reusable
private val modifierWidth = Modifier.fillMaxWidth()
// endregion

@ExperimentalMaterial3Api
@Composable
fun NotesTopBar(
    modifier: Modifier = Modifier,
    state: BaseNotesViewModel.State,
    onDeleteSelectedNotes: () -> Unit,
    onCancelSelect: () -> Unit,
    onMenuClick: () -> Unit
) {
    val (moreOptions, moreOptionsChanged) = remember {
        mutableStateOf(false)
    }

    val selectedNotesCount = remember(state) {
        derivedStateOf { (state as? BaseNotesViewModel.State.Success)?.selectedNotes?.size ?: 0 }
    }

    BackHandler(
        enabled = selectedNotesCount.value > 0,
        onBack = onCancelSelect
    )

    TopAppBar(
        modifier = modifier,
        windowInsets = WindowInsets.statusBars
            .union(WindowInsets.horizontalCutout)
            .union(WindowInsets.horizontalNavigationBars),
        navigationIcon = {
            AnimatedScale(
                targetState = selectedNotesCount.value > 0
            ) {
                if (it) {
                    DefaultIconButton(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_close),
                        onClick = onCancelSelect,
                        backgroundColor = Color.Transparent,
                        iconTint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = stringResource(R.string.close)
                    )
                } else {
                    DefaultIconButton(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_menu),
                        onClick = onMenuClick,
                        backgroundColor = Color.Transparent,
                        iconTint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = stringResource(R.string.menu)
                    )
                }
            }
        },
        actions = {
            AnimatedScale(
                targetState = selectedNotesCount.value > 0
            ) {
                if (it) {
                    DefaultIconButton(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_delete),
                        onClick = onDeleteSelectedNotes,
                        backgroundColor = Color.Transparent,
                        iconTint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = stringResource(R.string.delete)
                    )
                } else {
                    DefaultIconButton(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_more_vert),
                        backgroundColor = Color.Transparent,
                        iconTint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = stringResource(R.string.actions),
                        onClick = {
                            moreOptionsChanged(!moreOptions)
                        }
                    )
                }
            }
        },
        title = {
            when (state) {
                is BaseNotesViewModel.State.Loading -> {
                    LoadingContent(
                        modifier = modifierWidth,
                        title = stringResource(R.string.loading_title)
                    )
                }

                is BaseNotesViewModel.State.Success -> {
                    AnimatedFade(targetState = selectedNotesCount.value > 0) {
                        if (it) {
                            AnimatedSelectTopBar(
                                modifier = modifierWidth,
                                selected = state.selectedNotes.size
                            )

                        } else {
                            SimpleContent(
                                modifier = modifierWidth,
                                title = stringResource(id = R.string.notes_title),
                            )
                        }
                    }
                }

                is BaseNotesViewModel.State.Error -> {

                }
            }
        }
    )
}

@Composable
private fun LoadingContent(
    modifier: Modifier,
    title: String,
) = Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(8.dp)
) {
    Text(
        modifier = Modifier.weight(1f),
        text = title,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.titleMedium
    )
}


@Composable
private fun SimpleContent(
    modifier: Modifier,
    title: String,
) = Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(8.dp)
) {
    Text(
        modifier = Modifier.weight(1f),
        text = title,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.titleMedium
    )
}

