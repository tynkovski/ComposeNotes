package com.tynkovski.notes.presentation.components.anim

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

data class Digit(val digitChar: Char, val fullNumber: Int, val place: Int) {
    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Digit -> digitChar == other.digitChar
            else -> super.equals(other)
        }
    }

    operator fun compareTo(other: Digit): Int {
        return fullNumber.compareTo(other.fullNumber)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedNumber(
    textStyle: TextStyle,
    value: Int,
    modifier: Modifier = Modifier,
) = Row(
    modifier = modifier.animateContentSize(),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
) {
    value.toString()
        .mapIndexed { index, c -> Digit(c, value, index) }
        .forEach { digit ->
            AnimatedContent(
                modifier = Modifier,
                targetState = digit,
                transitionSpec = {
                    val (start, end) = if (targetState > initialState) {
                        slideInVertically { -it } to slideOutVertically { it }
                    } else {
                        slideInVertically { it } to slideOutVertically { -it }
                    }
                    (start with end).using(SizeTransform(clip = false))
                }
            ) { digit ->
                Text(
                    text = "${digit.digitChar}",
                    style = textStyle.copy(letterSpacing = 0.sp),
                    textAlign = TextAlign.Center,
                )
            }
        }
}
