package com.tynkovski.notes.presentation.utils

import android.graphics.BlurMaskFilter
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas

data class ShadowModifier(
    val shadow: Shadow,
    val shape: Shape,
) : DrawModifier {
    override fun ContentDrawScope.draw() {
        drawIntoCanvas { canvas ->
            val paint = Paint().apply {
                color = shadow.color
                asFrameworkPaint().apply {
                    maskFilter = BlurMaskFilter(
                        convertRadiusToSigma(shadow.blurRadius),
                        BlurMaskFilter.Blur.NORMAL
                    )
                }
            }
            shape.createOutline(
                size = size,
                layoutDirection = layoutDirection,
                density = this
            ).let { outline ->
                canvas.drawWithOffset(offset = shadow.offset) {
                    when (outline) {
                        is Outline.Rectangle -> {
                            drawRect(rect = outline.rect, paint = paint)
                        }
                        is Outline.Rounded -> {
                            drawPath(
                                path = Path().apply { addRoundRect(outline.roundRect) },
                                paint = paint
                            )
                        }
                        is Outline.Generic -> {
                            drawPath(path = outline.path, paint = paint)
                        }
                    }
                }
            }
        }
        drawContent()
    }

    private fun convertRadiusToSigma(
        radius: Float,
        enable: Boolean = true,
    ): Float = if (enable) {
        (radius * 0.57735 + 0.5).toFloat()
    } else {
        radius
    }

    private fun Canvas.drawWithOffset(
        offset: Offset,
        block: Canvas.() -> Unit,
    ) {
        save()
        translate(offset.x, offset.y)
        block()
        restore()
    }
}