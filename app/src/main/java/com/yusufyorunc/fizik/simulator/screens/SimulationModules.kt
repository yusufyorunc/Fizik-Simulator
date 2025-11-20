package com.yusufyorunc.fizik.simulator.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yusufyorunc.fizik.simulator.NativeLibrary
import com.yusufyorunc.fizik.simulator.ui.*
import org.json.JSONObject

@Composable
fun FreeFallScreen(onBackClick: () -> Unit) {
    var time by remember { mutableStateOf(2.0f) }
    var height by remember { mutableStateOf(100.0f) }
    var resultText by remember { mutableStateOf("") }

    // Calculate on change
    LaunchedEffect(time, height) {
        val jsonStr = NativeLibrary.safeCalculateFreeFall(time.toDouble(), height.toDouble())
        try {
            val json = JSONObject(jsonStr)
            resultText = """
                ⏱️ Süre: ${json.optDouble("time")} s
                📏 Başlangıç: ${json.optDouble("initialHeight")} m
                ⚡ Son Hız: %.2f m/s
                📉 Düşülen: %.2f m
                📍 Kalan: %.2f m
            """.trimIndent().format(
                json.optDouble("finalVelocity"),
                json.optDouble("distanceFallen"),
                json.optDouble("remainingHeight")
            )
        } catch (e: Exception) {
            resultText = "Hesaplama Hatası"
        }
    }

    SimulationScreen(title = "Serbest Düşüş", onBackClick = onBackClick) {
        // Visual
        SimulationCard(title = "Simülasyon") {
            Column(modifier = Modifier.fillMaxWidth().height(200.dp)) {
                // Animate progress based on time (just a visual approximation)
                // In a real app we'd run an animation loop. 
                // Here we show the state at 'time'.
                // To make it look like an animation, we could animate 'time' from 0 to target.
                // But for now, let's show the static state at the selected time.
                // Or better, let's map 'time' to progress 0..1 relative to total fall time.
                // t_total = sqrt(2h/g)
                val totalTime = Math.sqrt(2 * height.toDouble() / 9.81).toFloat()
                val progress = (time / totalTime).coerceIn(0f, 1f)
                
                FreeFallVisualizer(progress = progress)
            }
        }

        // Inputs
        SimulationCard(title = "Parametreler") {
            InputSlider(label = "Süre (s)", value = time, range = 0f..10f) { time = it }
            InputSlider(label = "Yükseklik (m)", value = height, range = 10f..200f) { height = it }
        }

        // Results
        SimulationCard(title = "Sonuçlar") {
            ResultText(resultText)
        }
    }
}

@Composable
fun NewtonScreen(onBackClick: () -> Unit) {
    var mass by remember { mutableStateOf(10.0f) }
    var acceleration by remember { mutableStateOf(5.0f) }
    var friction by remember { mutableStateOf(0.2f) }
    var resultText by remember { mutableStateOf("") }

    LaunchedEffect(mass, acceleration, friction) {
        val jsonStr = NativeLibrary.safeCalculateNewtonSecondLaw(mass.toDouble(), acceleration.toDouble(), friction.toDouble())
        try {
            val json = JSONObject(jsonStr)
            resultText = """
                💪 Uygulanan Kuvvet: %.2f N
                🔥 Sürtünme: %.2f N
                ⚡ Net Kuvvet: %.2f N
            """.trimIndent().format(
                json.optDouble("appliedForce"),
                json.optDouble("frictionForce"),
                json.optDouble("netForce")
            )
        } catch (e: Exception) {
            resultText = "Hata"
        }
    }

    SimulationScreen(title = "Newton'un 2. Yasası", onBackClick = onBackClick) {
        SimulationCard(title = "Simülasyon") {
            Column(modifier = Modifier.fillMaxWidth().height(200.dp)) {
                NewtonVisualizer(acceleration = acceleration / 10f) // Scale for visual
            }
        }

        SimulationCard(title = "Parametreler") {
            InputSlider(label = "Kütle (kg)", value = mass, range = 1f..100f) { mass = it }
            InputSlider(label = "İvme (m/s²)", value = acceleration, range = 0f..20f) { acceleration = it }
            InputSlider(label = "Sürtünme Katsayısı", value = friction, range = 0f..1f) { friction = it }
        }

        SimulationCard(title = "Sonuçlar") {
            ResultText(resultText)
        }
    }
}

@Composable
fun ProjectileScreen(onBackClick: () -> Unit) {
    var velocity by remember { mutableStateOf(50.0f) }
    var angle by remember { mutableStateOf(45.0f) }
    var resultText by remember { mutableStateOf("") }

    LaunchedEffect(velocity, angle) {
        val jsonStr = NativeLibrary.safeCalculateProjectileMotion(velocity.toDouble(), angle.toDouble())
        try {
            val json = JSONObject(jsonStr)
            resultText = """
                ⏱️ Uçuş Süresi: %.2f s
                📏 Maks Yükseklik: %.2f m
                🎯 Menzil: %.2f m
            """.trimIndent().format(
                json.optDouble("flightTime"),
                json.optDouble("maxHeight"),
                json.optDouble("range")
            )
        } catch (e: Exception) {
            resultText = "Hata"
        }
    }

    SimulationScreen(title = "Eğik Atış", onBackClick = onBackClick) {
        SimulationCard(title = "Simülasyon") {
            Column(modifier = Modifier.fillMaxWidth().height(200.dp)) {
                ProjectileVisualizer(angle = angle)
            }
        }

        SimulationCard(title = "Parametreler") {
            InputSlider(label = "Hız (m/s)", value = velocity, range = 10f..100f) { velocity = it }
            InputSlider(label = "Açı (derece)", value = angle, range = 0f..90f) { angle = it }
        }

        SimulationCard(title = "Sonuçlar") {
            ResultText(resultText)
        }
    }
}

@Composable
fun PendulumScreen(onBackClick: () -> Unit) {
    var length by remember { mutableStateOf(2.0f) }
    var angle by remember { mutableStateOf(30.0f) }
    var resultText by remember { mutableStateOf("") }

    LaunchedEffect(length, angle) {
        val jsonStr = NativeLibrary.safeCalculatePendulum(length.toDouble(), angle.toDouble())
        try {
            val json = JSONObject(jsonStr)
            resultText = """
                ⏱️ Periyot: %.2f s
                ⚡ Maks Hız: %.2f m/s
            """.trimIndent().format(
                json.optDouble("period"),
                json.optDouble("maxVelocity")
            )
        } catch (e: Exception) {
            resultText = "Hata"
        }
    }

    SimulationScreen(title = "Basit Sarkaç", onBackClick = onBackClick) {
        SimulationCard(title = "Simülasyon") {
            Column(modifier = Modifier.fillMaxWidth().height(200.dp)) {
                PendulumVisualizer(angle = angle)
            }
        }

        SimulationCard(title = "Parametreler") {
            InputSlider(label = "İp Uzunluğu (m)", value = length, range = 0.5f..5f) { length = it }
            InputSlider(label = "Başlangıç Açısı", value = angle, range = 0f..90f) { angle = it }
        }

        SimulationCard(title = "Sonuçlar") {
            ResultText(resultText)
        }
    }
}

@Composable
fun EnergyScreen(onBackClick: () -> Unit) {
    var mass by remember { mutableStateOf(5.0f) }
    var height by remember { mutableStateOf(10.0f) }
    var velocity by remember { mutableStateOf(0.0f) }
    var resultText by remember { mutableStateOf("") }
    var peRatio by remember { mutableStateOf(0f) }
    var keRatio by remember { mutableStateOf(0f) }

    LaunchedEffect(mass, height, velocity) {
        val jsonStr = NativeLibrary.safeCalculateEnergy(mass.toDouble(), height.toDouble(), velocity.toDouble())
        try {
            val json = JSONObject(jsonStr)
            val pe = json.optDouble("potentialEnergy")
            val ke = json.optDouble("kineticEnergy")
            val total = json.optDouble("totalEnergy")
            
            if (total > 0) {
                peRatio = (pe / total).toFloat()
                keRatio = (ke / total).toFloat()
            } else {
                peRatio = 0f
                keRatio = 0f
            }

            resultText = """
                ⚡ Potansiyel: %.2f J
                ⚡ Kinetik: %.2f J
                🔋 Toplam: %.2f J
            """.trimIndent().format(pe, ke, total)
        } catch (e: Exception) {
            resultText = "Hata"
        }
    }

    SimulationScreen(title = "Enerji Dönüşümü", onBackClick = onBackClick) {
        SimulationCard(title = "Simülasyon") {
            Column(modifier = Modifier.fillMaxWidth().height(200.dp)) {
                EnergyVisualizer(peRatio = peRatio, keRatio = keRatio)
            }
        }

        SimulationCard(title = "Parametreler") {
            InputSlider(label = "Kütle (kg)", value = mass, range = 1f..50f) { mass = it }
            InputSlider(label = "Yükseklik (m)", value = height, range = 0f..50f) { height = it }
            InputSlider(label = "Hız (m/s)", value = velocity, range = 0f..30f) { velocity = it }
        }

        SimulationCard(title = "Sonuçlar") {
            ResultText(resultText)
        }
    }
}
