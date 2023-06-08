package br.com.zemaromba.core_ui.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = lightColorScheme(
    primary = DarkMode.Primary,
    onPrimary = DarkMode.OnPrimary,
    primaryContainer = DarkMode.PrimaryContainer,
    onPrimaryContainer = DarkMode.OnPrimaryContainer,
    secondary = DarkMode.Secondary,
    onSecondary = DarkMode.OnSecondary,
    secondaryContainer = DarkMode.SecondaryContainer,
    onSecondaryContainer = DarkMode.OnSecondaryContainer,
    tertiary = DarkMode.Tertiary,
    onTertiary = DarkMode.OnTertiary,
    tertiaryContainer = DarkMode.TertiaryContainer,
    onTertiaryContainer = DarkMode.OnTertiaryContainer,
    error = DarkMode.Error,
    onError = DarkMode.OnError,
    errorContainer = DarkMode.ErrorContainer,
    onErrorContainer = DarkMode.OnErrorContainer,
    background = DarkMode.Background,
    onBackground = DarkMode.OnBackground,
    surface = DarkMode.Surface,
    onSurface = DarkMode.OnSurface,
    outline = DarkMode.Outline,
    surfaceVariant = DarkMode.SurfaceVariant,
    onSurfaceVariant = DarkMode.OnSurfaceVariant
)

private val LightColorScheme = lightColorScheme(
    primary = LightMode.Primary,
    onPrimary = LightMode.OnPrimary,
    primaryContainer = LightMode.PrimaryContainer,
    onPrimaryContainer = LightMode.OnPrimaryContainer,
    secondary = LightMode.Secondary,
    onSecondary = LightMode.OnSecondary,
    secondaryContainer = LightMode.SecondaryContainer,
    onSecondaryContainer = LightMode.OnSecondaryContainer,
    tertiary = LightMode.Tertiary,
    onTertiary = LightMode.OnTertiary,
    tertiaryContainer = LightMode.TertiaryContainer,
    onTertiaryContainer = LightMode.OnTertiaryContainer,
    error = LightMode.Error,
    onError = LightMode.OnError,
    errorContainer = LightMode.ErrorContainer,
    onErrorContainer = LightMode.OnErrorContainer,
    background = LightMode.Background,
    onBackground = LightMode.OnBackground,
    surface = LightMode.Surface,
    onSurface = LightMode.OnSurface,
    outline = LightMode.Outline,
    surfaceVariant = LightMode.SurfaceVariant,
    onSurfaceVariant = LightMode.OnSurfaceVariant
)

@Composable
fun ZeMarombaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}