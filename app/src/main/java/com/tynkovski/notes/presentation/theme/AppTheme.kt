package com.tynkovski.notes.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tynkovski.notes.presentation.theme.shape.appShapes
import com.tynkovski.notes.presentation.theme.typography.defaultTypography

private val colorScheme  = lightColorScheme(
    primary              = Color(0xFF_00_6D_38),
    onPrimary            = Color(0xFF_FF_FF_FF),
    primaryContainer     = Color(0xFF_8F_F9_AD),
    onPrimaryContainer   = Color(0xFF_00_21_0D),
    secondary            = Color(0xFF_50_63_53),
    onSecondary          = Color(0xFF_FF_FF_FF),
    secondaryContainer   = Color(0xFF_D2_E8_D3),
    onSecondaryContainer = Color(0xFF_0D_1F_12),
    tertiary             = Color(0xFF_3A_65_6F),
    onTertiary           = Color(0xFF_FF_FF_FF),
    tertiaryContainer    = Color(0xFF_BE_EA_F6),
    onTertiaryContainer  = Color(0xFF_00_1F_25),
    error                = Color(0xFF_BA_1A_1A),
    errorContainer       = Color(0xFF_FF_DA_D6),
    onError              = Color(0xFF_FF_FF_FF),
    onErrorContainer     = Color(0xFF_41_00_02),
    background           = Color(0xFF_FB_FD_F7),
    onBackground         = Color(0xFF_19_1C_19),
    surface              = Color(0xFF_FB_FD_F7),
    onSurface            = Color(0xFF_19_1C_19),
    surfaceVariant       = Color(0xFF_DD_E5_DB),
    onSurfaceVariant     = Color(0xFF_41_49_41),
    outline              = Color(0xFF_71_79_71),
    inverseOnSurface     = Color(0xFF_F0_F1_EC),
    inverseSurface       = Color(0xFF_2E_31_2E),
    inversePrimary       = Color(0xFF_72_DC_93),
    surfaceTint          = Color(0xFF_00_6D_38),
    outlineVariant       = Color(0xFF_C1_C9_BF),
    scrim                = Color(0xFF_00_00_00),
)

@Composable
fun NotesTheme(
    content: @Composable () -> Unit,
) {
    val systemUiController = rememberSystemUiController()

    DisposableEffect(systemUiController) {
        systemUiController.setSystemBarsColor(color = Color.Transparent, darkIcons = true)
        systemUiController.setNavigationBarColor(color = Color.Transparent, darkIcons = true, navigationBarContrastEnforced = false)
        onDispose { }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = defaultTypography,
        shapes = appShapes,
        content = content
    )
}
