package com.tynkovski.notes.presentation.components.anim

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.with
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun <T> AnimatedScale(
    targetState: T,
    modifier: Modifier = Modifier,
    content: @Composable (T) -> Unit,
) = AnimatedContent(
    targetState = targetState,
    modifier = modifier,
    transitionSpec = {
        val tweenSpec = tween<Float>(200)
        val start = scaleIn(animationSpec = tweenSpec)
        val end = scaleOut(animationSpec = tweenSpec)
        (start with end).using(SizeTransform(clip = false))
    }
) {
    content(it)
}
