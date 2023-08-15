package com.tynkovski.notes.presentation.components.indicator

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun DotsFlashing(
    modifier: Modifier,
    minAlpha: Float,
    delayUnit: Int,
    space: Dp,
    dotSize: Dp,
    color: Color = MaterialTheme.colorScheme.primary,
) {
    val infiniteTransition = rememberInfiniteTransition()

    @Composable
    fun animateAlphaWithDelay(delay: Int) = infiniteTransition.animateFloat(
        initialValue = minAlpha,
        targetValue = minAlpha,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = delayUnit * 4
                minAlpha at delay with FastOutLinearInEasing
                1f at delay + delayUnit with FastOutLinearInEasing
                minAlpha at delay + delayUnit * 2
            }
        )
    )

    val alpha1 by animateAlphaWithDelay(0)
    val alpha2 by animateAlphaWithDelay(delayUnit)
    val alpha3 by animateAlphaWithDelay(delayUnit * 2)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = space,
            alignment = Alignment.CenterHorizontally
        ),
    ) {
        Dot(modifier, dotSize, color, alpha1)
        Dot(modifier, dotSize, color, alpha2)
        Dot(modifier, dotSize, color, alpha3)
    }
}

@Composable
private fun Dot(
    modifier: Modifier,
    size: Dp,
    color: Color,
    alpha: Float,
) = Spacer(
    modifier = modifier
        .size(size)
        .alpha(alpha)
        .background(
            color = color,
            shape = CircleShape
        )
)