package com.tynkovski.notes.presentation.components.topBar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tynkovski.notes.R
import com.tynkovski.notes.presentation.components.anim.AnimatedNumber

@Composable
fun AnimatedSelectTopBar(
    modifier: Modifier,
    selected: Int,
) = Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
) {
    Text(
        modifier = Modifier,
        text = stringResource(id = R.string.top_bar_title_selected),
        style = MaterialTheme.typography.bodyLarge
    )

    Spacer(modifier = Modifier.width(4.dp))

    AnimatedNumber(
        textStyle = MaterialTheme.typography.bodyLarge,
        value = selected,
        modifier = Modifier
    )
}