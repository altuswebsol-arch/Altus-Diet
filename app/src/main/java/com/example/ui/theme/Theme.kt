package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val AltusLightColorScheme = lightColorScheme(
    primary = AltusSlate,
    secondary = AltusSage,
    tertiary = AltusAccent,
    background = AltusBackground,
    surface = AltusSurface,
    onPrimary = AltusSurface,
    onSecondary = AltusSurface,
    onTertiary = AltusSurface,
    onBackground = AltusSlate,
    onSurface = AltusSlate,
    surfaceVariant = AltusSageLight,
    onSurfaceVariant = AltusSlate
)

private val AltusDarkColorScheme = darkColorScheme(
    primary = AltusSageDark,
    secondary = AltusSlateDark,
    tertiary = AltusAccentDark,
    background = AltusBackgroundDark,
    surface = AltusSurfaceDark,
    onPrimary = AltusSlateDark,
    onSecondary = AltusSurface,
    onTertiary = AltusSurface,
    onBackground = AltusBackground,
    onSurface = AltusBackground,
    surfaceVariant = AltusSurfaceDark,
    onSurfaceVariant = AltusBackground
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // We disable dynamicColor by default to guarantee the handcrafted Altus branding
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        AltusDarkColorScheme
    } else {
        AltusLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
