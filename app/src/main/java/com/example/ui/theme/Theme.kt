package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val KineticArenaColorScheme = darkColorScheme(
    primary = AccentViolet,
    onPrimary = TextLight,
    primaryContainer = AccentVioletContainer,
    onPrimaryContainer = TextLight,
    secondary = SuccessNeon,
    onSecondary = ArenaBackground,
    secondaryContainer = SecondaryContainerGreen,
    onSecondaryContainer = TextLight,
    tertiary = TertiaryOrange,
    onTertiary = ArenaBackground,
    tertiaryContainer = TertiaryContainerOrange,
    onTertiaryContainer = TextLight,
    background = ArenaBackground,
    onBackground = TextLight,
    surface = ArenaSurface1,
    onSurface = TextLight,
    surfaceVariant = ArenaSurfaceContainer,
    onSurfaceVariant = TextMuted,
    outline = ArenaBorder,
    outlineVariant = OutlineVariant,
    error = DangerRed,
    errorContainer = DangerErrorContainer,
    onError = TextLight,
    onErrorContainer = TextLight
)

@Composable
fun RepBattleTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = KineticArenaColorScheme,
        typography = Typography,
        content = content
    )
}
