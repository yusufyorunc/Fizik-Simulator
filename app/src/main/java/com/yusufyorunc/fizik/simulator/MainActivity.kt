package com.yusufyorunc.fizik.simulator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.yusufyorunc.fizik.simulator.screens.home.HomeScreen
import androidx.compose.material3.Surface
import com.yusufyorunc.fizik.simulator.ui.theme.FizikSimulatorTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            val welcomeMessage = NativeLibrary.safeStringFromJNI()
            Toast.makeText(this, welcomeMessage, Toast.LENGTH_LONG).show()
        } catch (_: Exception) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }

        setContent {
            FizikSimulatorTheme {
                Surface {
                    HomeScreen()
                }
            }
        }
    }
}