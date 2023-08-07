package com.tynkovski.notes.presentation.components.indicator

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SpinningCircleProgressIndicator(
    modifier: Modifier = Modifier,
    staticItemColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    dynamicItemColor: Color = MaterialTheme.colorScheme.outline,
    durationMillis: Int = 600,
    count: Int = 8,
    indicatorSize: Dp = 20.dp,
) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = count.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(
        modifier = modifier
            .progressSemantics()
            .size(indicatorSize)
    ) {
        var canvasWidth = size.width
        var canvasHeight = size.height

        if (canvasHeight < canvasWidth) {
            canvasWidth = canvasHeight
        } else {
            canvasHeight = canvasWidth
        }

        val radius = canvasWidth * 0.12f

        val horizontalOffset = (size.width - size.height).coerceAtLeast(0f) / 2
        val verticalOffset = (size.height - size.width).coerceAtLeast(0f) / 2
        val center = Offset(
            x = horizontalOffset + canvasWidth - radius,
            y = verticalOffset + canvasHeight / 2
        )

        for (i in (0..360) step (360 / count)) {
            rotate(i.toFloat()) {
                drawCircle(
                    color = staticItemColor,
                    radius = radius,
                    center = center,
                )
            }
        }

        val coefficient = 360f / count

        for (i in 0..3) {
            rotate((angle.toInt() + i) * coefficient) {
                drawCircle(
                    color = dynamicItemColor.copy(
                        alpha = (i.toFloat() / 4f).coerceIn(
                            0f, 1f
                        )
                    ),
                    radius = radius,
                    center = center,
                )
            }
        }
    }
}