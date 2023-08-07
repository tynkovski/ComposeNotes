package com.tynkovski.notes.presentation.components.anim

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun <T> AnimatedFade(
    targetState: T,
    modifier: Modifier = Modifier,
    content: @Composable (T) -> Unit,
) = AnimatedContent(
    targetState = targetState,
    modifier = modifier,
    transitionSpec = {
        val tweenSpec = tween<Float>(200)
        val start = fadeIn(animationSpec = tweenSpec)
        val end = fadeOut(animationSpec = tweenSpec)
        (start with end).using(SizeTransform(clip = false))
    }
) {
    content(it)
}
