package com.tynkovski.notes.presentation.pages.detail.compontents

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.tynkovski.notes.presentation.components.menu.DefaultMenu
import com.tynkovski.notes.presentation.components.menu.DefaultMenuItem
import com.tynkovski.notes.presentation.utils.ext.color
import com.tynkovski.notes.presentation.utils.ext.horizontalCutout
import com.tynkovski.notes.presentation.utils.ext.horizontalNavigationBars

// region Reusable
private val modifierWidth = Modifier.fillMaxWidth()
// endregion

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBar(
    modifier: Modifier,
    edit: Boolean,
    newNote: Boolean,
    title: String,
    titleChanged: (String) -> Unit,
    color: Long,
    colorChanged: (Long) -> Unit,
    onSaveNote: () -> Unit,
    onStartEditNote: () -> Unit,
    onDeleteNote: () -> Unit,
    onBack: () -> Unit
) {
    val (moreOptions, moreOptionsChanged) = remember {
        mutableStateOf(false)
    }

    BackHandler(
        enabled = !edit,
        onBack = onBack
    )

    val tobBarColor = animateColorAsState(
        targetValue = if (color == -1L) MaterialTheme.colorScheme.surface else Color(color),
        label = "Top bar color animation"
    )

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = tobBarColor.value),
        modifier = modifier,
        windowInsets = WindowInsets.statusBars
            .union(WindowInsets.horizontalCutout)
            .union(WindowInsets.horizontalNavigationBars),
        navigationIcon = {
            AnimatedScale(targetState = edit) {
                if (it) {
                    DefaultIconButton(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_done),
                        onClick = onSaveNote,
                        backgroundColor = Color.Transparent,
                        iconTint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = stringResource(R.string.save)
                    )
                } else {
                    DefaultIconButton(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back_ios_new),
                        onClick = onBack,
                        backgroundColor = Color.Transparent,
                        iconTint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        },
        title = {
            AnimatedFade(targetState = edit) {
                if (it) {
                    BasicTextField(
                        value = title,
                        onValueChange = titleChanged,
                        modifier = modifierWidth,
                        singleLine = true,
                        maxLines = 1,
                        textStyle = MaterialTheme.typography.titleMedium.color(MaterialTheme.colorScheme.onSurface)
                    )
                } else {
                    Text(
                        modifier = modifierWidth.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = onStartEditNote
                        ),
                        text = title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        },
        actions = {
            DefaultIconButton(
                imageVector = ImageVector.vectorResource(R.drawable.ic_more_vert),
                backgroundColor = Color.Transparent,
                iconTint = MaterialTheme.colorScheme.onSurface,
                onClick = {
                    moreOptionsChanged(!moreOptions)
                }
            )

            DefaultMenu(
                modifier = Modifier,
                expanded = moreOptions,
                onDismissRequest = { moreOptionsChanged(!moreOptions) }
            ) {
                DefaultMenuItem(
                    text = stringResource(id = R.string.edit),
                    leadingIcon = ImageVector.vectorResource(R.drawable.ic_edit),
                    onClick = {
                        onStartEditNote()
                        moreOptionsChanged(false)
                    }
                )

                if (!newNote) {
                    DefaultMenuItem(
                        text = stringResource(id = R.string.delete),
                        leadingIcon = ImageVector.vectorResource(R.drawable.ic_delete),
                        onClick = onDeleteNote
                    )
                }

                val (showColorDialog, showColorDialogChanged) = remember {
                    mutableStateOf(false)
                }

                DropdownMenuItem(
                    text = { Text(text = stringResource(id = R.string.detail_note_menu_title)) },
                    modifier = modifier,
                    onClick = {
                        showColorDialogChanged(true)
                        onStartEditNote()
                    },
                    leadingIcon = {
                        if (color == -1L) {
                            Spacer(
                                modifier = Modifier
                                    .padding(start = 3.dp)
                                    .size(18.dp)
                                    .border(2.dp, color = Color.White, shape = CircleShape)
                            )
                        } else {
                            Spacer(
                                modifier = Modifier
                                    .padding(start = 3.dp)
                                    .size(18.dp)
                                    .background(color = Color(color), shape = CircleShape)
                            )
                        }
                    }
                )

                ColorDialog(
                    visible = showColorDialog,
                    currentColor = color,
                    onDismiss = { showColorDialogChanged(false) },
                    onColorSelected = colorChanged
                )
            }
        }
    )
}