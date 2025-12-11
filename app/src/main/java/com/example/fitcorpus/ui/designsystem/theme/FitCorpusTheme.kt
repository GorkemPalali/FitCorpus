package com.example.fitcorpus.ui.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF2979FF),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF1E5ACC),
    onPrimaryContainer = Color.White,
    secondary = Color(0xFF76FF03),
    onSecondary = Color(0xFF121212),
    secondaryContainer = Color(0xFF5ACC02),
    onSecondaryContainer = Color(0xFF121212),
    tertiary = Color(0xFF76FF03),
    onTertiary = Color(0xFF121212),
    background = Color(0xFF121212),
    onBackground = Color(0xFFF5F5F5),
    surface = Color(0xFF333333),
    onSurface = Color(0xFFF5F5F5),
    surfaceVariant = Color(0xFF424242),
    onSurfaceVariant = Color(0xFFBDBDBD),
    error = Color(0xFFFF5252),
    onError = Color.White,
    errorContainer = Color(0xFFCC0000),
    onErrorContainer = Color.White,
    outline = Color(0xFF424242),
    outlineVariant = Color(0xFF7A7A7A),
    scrim = Color(0xFF000000),
    inverseSurface = Color(0xFFF5F5F5),
    inverseOnSurface = Color(0xFF121212),
    inversePrimary = Color(0xFF2979FF),
    surfaceDim = Color(0xFF121212),
    surfaceBright = Color(0xFF424242),
    surfaceContainerLowest = Color(0xFF0A0A0A),
    surfaceContainerLow = Color(0xFF1E1E1E),
    surfaceContainer = Color(0xFF2A2A2A),
    surfaceContainerHigh = Color(0xFF363636),
    surfaceContainerHighest = Color(0xFF424242)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2979FF),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE3F2FD),
    onPrimaryContainer = Color(0xFF0D47A1),
    secondary = Color(0xFF76FF03),
    onSecondary = Color(0xFF121212),
    secondaryContainer = Color(0xFFE8F5E9),
    onSecondaryContainer = Color(0xFF1B5E20),
    tertiary = Color(0xFF76FF03),
    onTertiary = Color(0xFF121212),
    background = Color(0xFFF5F5F5),
    onBackground = Color(0xFF121212),
    surface = Color.White,
    onSurface = Color(0xFF121212),
    surfaceVariant = Color(0xFFE0E0E0),
    onSurfaceVariant = Color(0xFF424242),
    error = Color(0xFFFF5252),
    onError = Color.White,
    errorContainer = Color(0xFFFFEBEE),
    onErrorContainer = Color(0xFFB71C1C),
    outline = Color(0xFF757575),
    outlineVariant = Color(0xFFBDBDBD),
    scrim = Color(0xFF000000),
    inverseSurface = Color(0xFF121212),
    inverseOnSurface = Color(0xFFF5F5F5),
    inversePrimary = Color(0xFF64B5F6),
    surfaceDim = Color(0xFFE0E0E0),
    surfaceBright = Color(0xFFFFFFFF),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFFAFAFA),
    surfaceContainer = Color(0xFFF5F5F5),
    surfaceContainerHigh = Color(0xFFEEEEEE),
    surfaceContainerHighest = Color(0xFFE0E0E0)
)

@Composable
fun FitCorpusTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    
    MaterialTheme(
        colorScheme = colorScheme,
        typography = FitCorpusTypography,
        content = content
    )
}