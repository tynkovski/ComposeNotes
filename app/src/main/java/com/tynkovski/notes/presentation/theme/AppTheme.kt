package com.tynkovski.notes.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tynkovski.notes.presentation.theme.shape.appShapes
import com.tynkovski.notes.presentation.theme.typography.defaultTypography

private val lightColorScheme  = lightColorScheme(
    primary = Color(0xFF006B56),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF7EF8D5),
    onPrimaryContainer = Color(0xFF002018),
    secondary = Color(0xFF4B635B),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFCEE9DD),
    onSecondaryContainer = Color(0xFF072019),
    tertiary = Color(0xFF416276),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFC4E7FF),
    onTertiaryContainer = Color(0xFF001E2C),
    error = Color(0xFFBA1A1A),
    errorContainer = Color(0xFFFFDAD6),
    onError = Color(0xFFFFFFFF),
    onErrorContainer = Color(0xFF410002),
    background = Color(0xFFFBFDFA),
    onBackground = Color(0xFF191C1B),
    surface = Color(0xFFFBFDFA),
    onSurface = Color(0xFF191C1B),
    surfaceVariant = Color(0xFFDBE5DF),
    onSurfaceVariant = Color(0xFF3F4945),
    outline = Color(0xFF6F7975),
    inverseOnSurface = Color(0xFFEFF1EE),
    inverseSurface = Color(0xFF2E312F),
    inversePrimary = Color(0xFF5FDBB9),
    surfaceTint = Color(0xFF006B56),
    outlineVariant = Color(0xFFBFC9C3),
)

private val darkColorScheme  = lightColorScheme(
    primary = Color(0xFF5FDBB9),
    onPrimary = Color(0xFF00382C),
    primaryContainer = Color(0xFF005140),
    onPrimaryContainer = Color(0xFF7EF8D5),
    secondary = Color(0xFFB2CCC2),
    onSecondary = Color(0xFF1D352D),
    secondaryContainer = Color(0xFF344C43),
    onSecondaryContainer = Color(0xFFCEE9DD),
    tertiary = Color(0xFFA8CBE2),
    onTertiary = Color(0xFF0E3446),
    tertiaryContainer = Color(0xFF284B5E),
    onTertiaryContainer = Color(0xFFC4E7FF),
    error = Color(0xFFFFB4AB),
    errorContainer = Color(0xFF93000A),
    onError = Color(0xFF690005),
    onErrorContainer = Color(0xFFFFDAD6),
    background = Color(0xFF191C1B),
    onBackground = Color(0xFFE1E3E0),
    surface = Color(0xFF191C1B),
    onSurface = Color(0xFFE1E3E0),
    surfaceVariant = Color(0xFF3F4945),
    onSurfaceVariant = Color(0xFFBFC9C3),
    outline = Color(0xFF89938E),
    inverseOnSurface = Color(0xFF191C1B),
    inverseSurface = Color(0xFFE1E3E0),
    inversePrimary = Color(0xFF006B56),
    surfaceTint = Color(0xFF5FDBB9),
    outlineVariant = Color(0xFF3F4945),
)

@Composable
fun NotesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()

    val colorScheme = if (darkTheme) darkColorScheme else lightColorScheme

    DisposableEffect(systemUiController) {
        systemUiController.setSystemBarsColor(color = Color.Transparent, darkIcons = !darkTheme)
        systemUiController.setNavigationBarColor(color = Color.Transparent, darkIcons = !darkTheme, navigationBarContrastEnforced = false)
        onDispose { }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = defaultTypography,
        shapes = appShapes,
        content = content
    )
}
