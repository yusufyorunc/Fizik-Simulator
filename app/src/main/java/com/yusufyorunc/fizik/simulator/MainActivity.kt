package com.yusufyorunc.fizik.simulator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.yusufyorunc.fizik.simulator.screens.*
import com.yusufyorunc.fizik.simulator.screens.home.HomeScreen
import com.yusufyorunc.fizik.simulator.ui.theme.FizikSimulatorTheme

enum class Screen {
    HOME, FREE_FALL, NEWTON, PROJECTILE, PENDULUM, ENERGY
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initial native check
        try {
            NativeLibrary.safeStringFromJNI()
        } catch (_: Exception) { }

        setContent {
            FizikSimulatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF121212)
                ) {
                    var currentScreen by remember { mutableStateOf(Screen.HOME) }

                    when (currentScreen) {
                        Screen.HOME -> HomeScreen(
                            onNavigate = { screen -> currentScreen = screen }
                        )
                        Screen.FREE_FALL -> FreeFallScreen(onBackClick = { currentScreen = Screen.HOME })
                        Screen.NEWTON -> NewtonScreen(onBackClick = { currentScreen = Screen.HOME })
                        Screen.PROJECTILE -> ProjectileScreen(onBackClick = { currentScreen = Screen.HOME })
                        Screen.PENDULUM -> PendulumScreen(onBackClick = { currentScreen = Screen.HOME })
                        Screen.ENERGY -> EnergyScreen(onBackClick = { currentScreen = Screen.HOME })
                    }
                }
            }
        }
    }
}