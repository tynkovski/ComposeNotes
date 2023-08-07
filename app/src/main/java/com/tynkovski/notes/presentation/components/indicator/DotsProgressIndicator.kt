package com.tynkovski.notes.presentation.components.indicator

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private fun calculateFraction(start: Float, end: Float, pos: Float) =
    (if (end - start == 0f) 0f else (pos - start) / (end - start)).coerceIn(0f, 1f)

private fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return (1 - fraction) * start + fraction * stop
}

private fun scale(start1: Float, end1: Float, pos: Float, start2: Float, end2: Float) =
    lerp(start2, end2, calculateFraction(start1, end1, pos))

@Composable
fun BouncingDotProgressIndicator(
    modifier: Modifier = Modifier,
    initialColor: Color,
    animatedColor: Color,
    indicatorSize: Dp = 16.dp,
) {
    val dotAnimatables = remember {
        listOf(Animatable(0f), Animatable(0f), Animatable(0f))
    }

    dotAnimatables.forEachIndexed { index, animatable ->
        LaunchedEffect(animatable) {
            animatable.animateTo(
                targetValue = 1f, animationSpec = infiniteRepeatable(
                    initialStartOffset = StartOffset(index * 150),
                    animation = keyframes {
                        durationMillis = 1000
                        0.0f at 0 with LinearOutSlowInEasing
                        1.0f at 300 with LinearOutSlowInEasing
                        0f at 700 with LinearOutSlowInEasing
                        0f at 1000
                    },
                    repeatMode = RepeatMode.Restart,
                )
            )
        }
    }

    val sameColor = initialColor == animatedColor

    Canvas(
        modifier
            .progressSemantics()
            .size(indicatorSize, indicatorSize / 2)
    ) {
        val canvasWidth = size.width * 0.875f
        val canvasHeight = size.height

        val diameter = (canvasHeight / 3).coerceAtLeast(canvasWidth / 3)
        val radius = diameter / 2f
        val offset = radius / 2f

        dotAnimatables.forEachIndexed { index, animatable ->
            val x = radius + index * (diameter) + index * offset
            val value = animatable.value

            drawCircle(
                color = if (sameColor) initialColor else lerp(
                    initialColor, animatedColor, value
                ),
                center = Offset(x = x, y = center.y - radius * value * 1.6f),
                radius = radius
            )
        }
    }
}

@Composable
fun DotProgressIndicator(
    modifier: Modifier = Modifier,
    initialColor: Color,
    animatedColor: Color,
    indicatorSize: Dp = 16.dp,
) {
    val initialValue = 0.25f

    val dotAnimatables = remember {
        listOf(Animatable(initialValue), Animatable(initialValue), Animatable(initialValue))
    }

    dotAnimatables.forEachIndexed { index, animatable ->
        LaunchedEffect(animatable) {
            animatable.animateTo(
                targetValue = 1f, animationSpec = infiniteRepeatable(
                    initialStartOffset = StartOffset(index * 300),
                    animation = tween(800, easing = FastOutLinearInEasing),
                    repeatMode = RepeatMode.Reverse,
                )
            )
        }
    }

    val sameColor = initialColor == animatedColor

    Canvas(
        modifier = modifier
            .progressSemantics()
            .size(indicatorSize, indicatorSize / 2)
    ) {
        val canvasWidth = size.width * 0.875f
        val canvasHeight = size.height

        val diameter = (canvasHeight / 3).coerceAtLeast(canvasWidth / 3)
        val radius = diameter / 2f
        val offset = radius / 2f
        dotAnimatables.forEachIndexed { index, animatable ->
            val x = radius + index * (diameter) + index * offset
            val value = animatable.value
            val colorFraction = scale(
                start1 = initialValue,
                end1 = 1f,
                pos = value,
                start2 = 0f,
                end2 = 1f
            )

            drawCircle(
                color = if (sameColor) initialColor.copy(alpha = value) else lerp(
                    initialColor, animatedColor, colorFraction
                ),
                center = Offset(x = x, y = center.y),
                radius = radius
            )
        }
    }
}