package com.yusufyorunc.fizik.simulator.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF00E676), // Neon Green
    secondary = Color(0xFF2979FF), // Neon Blue
    tertiary = Color(0xFFD500F9), // Neon Purple
    background = Color(0xFF121212), // Dark Background
    surface = Color(0xFF1E1E1E), // Slightly lighter surface
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
)

@Composable
fun FizikSimulatorTheme(
    useDarkTheme: Boolean = true, // Force dark theme for "Vibe Coding"
    dynamicColor: Boolean = false, // Disable dynamic color to maintain aesthetic
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

