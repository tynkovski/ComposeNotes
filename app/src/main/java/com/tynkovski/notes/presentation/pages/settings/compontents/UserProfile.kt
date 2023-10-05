package com.tynkovski.notes.presentation.pages.settings.compontents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tynkovski.notes.R
import com.tynkovski.notes.data.remote.models.ErrorException
import com.tynkovski.notes.presentation.components.button.DefaultButton
import com.tynkovski.notes.presentation.components.shimmer.DefaultShimmer
import com.tynkovski.notes.presentation.utils.ext.normal

@Composable
fun UserProfile(
    modifier: Modifier,
    shape: RoundedCornerShape,
    login: String,
    name: String
) = Card(
    modifier = modifier,
    shape = shape,
    colors = CardDefaults.elevatedCardColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    )
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        UserBlock(
            title = stringResource(R.string.profile_screen_title_user_login),
            description = login
        )

        Divider(color = MaterialTheme.colorScheme.background)

        UserBlock(
            title = stringResource(R.string.profile_screen_title_user_name),
            description = name
        )

        Divider(color = MaterialTheme.colorScheme.background)

        UserBlock(
            title = stringResource(R.string.profile_screen_title_user_password),
            description = "••••••••"
        )
    }
}

@Composable
private fun UserBlock(
    title: String,
    description: String,
    onClick: () -> Unit = {}
) = Column(
    modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = onClick)
        .padding(horizontal = 16.dp)
) {
    Spacer(modifier = Modifier.height(8.dp))

    Text(
        modifier = Modifier,
        style = MaterialTheme.typography.titleMedium,
        text = title,
        color = MaterialTheme.colorScheme.outline
    )

    Spacer(modifier = Modifier.width(8.dp))

    Text(
        modifier = Modifier,
        style = MaterialTheme.typography.titleMedium.normal,
        text = description,
    )

    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun UserProfileLoading(
    modifier: Modifier,
    shape: RoundedCornerShape,
) = DefaultShimmer(
    modifier = modifier.height(112.dp),
    color = MaterialTheme.colorScheme.secondary,
    shape = shape
)

@Composable
fun UserProfileError(
    modifier: Modifier,
    shape: RoundedCornerShape,
    error: ErrorException,
    onRefresh: () -> Unit
) = Card(
    modifier = modifier,
    shape = shape,
    colors = CardDefaults.elevatedCardColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    )
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleLarge,
            text = stringResource(R.string.profile_screen_get_user_error),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        DefaultButton(
            onClick = onRefresh,
            text = stringResource(R.string.button_refresh),
            contentPadding = PaddingValues(horizontal = 32.dp)
        )
    }
}