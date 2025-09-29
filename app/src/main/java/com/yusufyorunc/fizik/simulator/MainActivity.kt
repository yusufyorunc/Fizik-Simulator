package com.yusufyorunc.fizik.simulator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.yusufyorunc.fizik.simulator.screens.home.HomeScreen
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            val welcomeMessage = NativeLibrary.safeStringFromJNI()
            Toast.makeText(this, welcomeMessage, Toast.LENGTH_LONG).show()
        } catch (_: Exception) {
            Toast.makeText(this, "Fizik Simülatörüne Hoş Geldiniz!", Toast.LENGTH_SHORT).show()
        }

        setContent {
            MaterialTheme {
                Surface {
                    HomeScreen()
                }
            }
        }
    }
}