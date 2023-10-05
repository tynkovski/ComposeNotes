package com.tynkovski.notes.presentation.components.shimmer

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tynkovski.notes.presentation.theme.NotesTheme
import com.valentinilk.shimmer.LocalShimmerTheme
import com.valentinilk.shimmer.ShimmerTheme
import com.valentinilk.shimmer.shimmer

val smoothShimmerAnimation: ShimmerTheme = ShimmerTheme(
    animationSpec = infiniteRepeatable(
        animation = tween(
            800,
            easing = LinearEasing,
            delayMillis = 1_500,
        ),
        repeatMode = RepeatMode.Restart,
    ),
    blendMode = BlendMode.DstIn,
    rotation = 15.0f,
    shaderColors = listOf(
        Color.Unspecified.copy(alpha = 0.1f),
        Color.Unspecified.copy(alpha = 0.2f),
        Color.Unspecified.copy(alpha = 0.1f),
    ),
    shaderColorStops = listOf(
        0.0f,
        0.5f,
        1.0f,
    ),
    shimmerWidth = 800.dp,
)

@Composable
fun ShimmerContent(
    modifier: Modifier,
    color: Color,
    shape: Shape,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: @Composable () -> Unit,
) = CompositionLocalProvider(
    LocalShimmerTheme provides smoothShimmerAnimation
) {
    Box(
        modifier = modifier
            .shimmer()
            .background(color, shape)
            .padding(contentPadding)
    ) {
        content()
    }
}

@Composable
fun DefaultShimmer(
    modifier: Modifier,
    color: Color,
    shape: Shape,
) = CompositionLocalProvider(
    LocalShimmerTheme provides smoothShimmerAnimation
) {
    Spacer(
        modifier = modifier
            .shimmer()
            .background(color, shape)
    )
}

@Preview(showBackground = true)
@Composable
private fun DefaultShimmerPreview() {
    NotesTheme(
        darkTheme = false
    ) {
        DefaultShimmer(
            modifier = Modifier.size(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            shape = CircleShape
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MessageShimmerPreview() {
    NotesTheme(
        darkTheme = false
    ) {
        DefaultShimmer(
            modifier = Modifier
                .width(200.dp)
                .height(170.dp),
            color = MaterialTheme.colorScheme.secondary,
            shape = RoundedCornerShape(12.dp)
        )
    }
}