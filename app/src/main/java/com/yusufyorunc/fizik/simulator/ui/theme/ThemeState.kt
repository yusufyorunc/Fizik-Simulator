package com.yusufyorunc.fizik.simulator.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

enum class ThemeMode {
    LIGHT, DARK, SYSTEM
}

class ThemeState(initialMode: ThemeMode = ThemeMode.SYSTEM) {
    var themeMode by mutableStateOf(initialMode)
        private set

    fun toggleTheme() {
        themeMode = when (themeMode) {
            ThemeMode.LIGHT -> ThemeMode.DARK
            ThemeMode.DARK -> ThemeMode.LIGHT
            ThemeMode.SYSTEM -> ThemeMode.LIGHT
        }
    }

    fun setThemeMode(mode: ThemeMode) {
        themeMode = mode
    }

    @Composable
    fun isDarkTheme(): Boolean {
        return when (themeMode) {
            ThemeMode.LIGHT -> false
            ThemeMode.DARK -> true
            ThemeMode.SYSTEM -> isSystemInDarkTheme()
        }
    }
}

@Composable
fun rememberThemeState(initialMode: ThemeMode = ThemeMode.SYSTEM): ThemeState {
    return remember { ThemeState(initialMode) }
}
