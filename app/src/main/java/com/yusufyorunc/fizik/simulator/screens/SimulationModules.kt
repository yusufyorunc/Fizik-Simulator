package com.yusufyorunc.fizik.simulator.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yusufyorunc.fizik.simulator.NativeLibrary
import com.yusufyorunc.fizik.simulator.R
import com.yusufyorunc.fizik.simulator.ui.*
import org.json.JSONObject

@Composable
fun FreeFallScreen(onBackClick: () -> Unit) {
    var inputTime by remember { mutableStateOf(2.0f) }
    var height by remember { mutableStateOf(100.0f) }
    var resultText by remember { mutableStateOf("") }
    
    // Animation state
    var isPlaying by remember { mutableStateOf(false) }
    var animationTime by remember { mutableStateOf(0f) }
    
    val resultTimeTemplate = stringResource(R.string.result_time)
    val resultInitialHeightTemplate = stringResource(R.string.result_initial_height)
    val resultFinalVelocityTemplate = stringResource(R.string.result_final_velocity)
    val resultDistanceFallenTemplate = stringResource(R.string.result_distance_fallen)
    val resultRemainingHeightTemplate = stringResource(R.string.result_remaining_height)
    val errorText = stringResource(R.string.error_calculation)

    // Animation Loop
    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            val startTime = System.nanoTime()
            val startAnimTime = animationTime
            while (true) {
                withFrameNanos { frameTimeNanos ->
                    val dt = (frameTimeNanos - startTime) / 1_000_000_000f // seconds elapsed since start of this play session
                    animationTime = startAnimTime + dt
                }
                
                if (animationTime >= inputTime) {
                    animationTime = inputTime
                    isPlaying = false
                    break
                }
            }
        }
    }

    // Calculate based on current animation time (or input time if not playing/reset)
    LaunchedEffect(animationTime, height) {
        val jsonStr = NativeLibrary.safeCalculateFreeFall(animationTime.toDouble(), height.toDouble())
        try {
            val json = JSONObject(jsonStr)
            resultText = """
                $resultTimeTemplate
                $resultInitialHeightTemplate
                $resultFinalVelocityTemplate
                $resultDistanceFallenTemplate
                $resultRemainingHeightTemplate
            """.trimIndent().format(
                json.optDouble("time"),
                json.optDouble("initialHeight"),
                json.optDouble("finalVelocity"),
                json.optDouble("distanceFallen"),
                json.optDouble("remainingHeight")
            )
        } catch (e: Exception) {
            resultText = errorText
        }
    }

    SimulationScreen(title = stringResource(R.string.module_free_fall), onBackClick = onBackClick) {
        // Visual
        SimulationCard(title = stringResource(R.string.simulation_title)) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    val totalFallTime = Math.sqrt(2 * height.toDouble() / 9.81).toFloat()
                    val progress = (animationTime / totalFallTime).coerceIn(0f, 1f)
                    FreeFallVisualizer(progress = progress)
                }
                
                // Controls
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = { isPlaying = !isPlaying }) {
                        Text(if (isPlaying) stringResource(R.string.btn_stop) else stringResource(R.string.btn_start))
                    }
                    Button(onClick = { 
                        isPlaying = false
                        animationTime = 0f 
                    }) {
                        Text(stringResource(R.string.btn_reset))
                    }
                }
            }
        }

        // Inputs
        SimulationCard(title = stringResource(R.string.parameters_title)) {
            InputSlider(label = stringResource(R.string.param_time), value = inputTime, range = 0.1f..10f) { 
                inputTime = it
            }
            InputSlider(label = stringResource(R.string.param_height), value = height, range = 10f..200f) { 
                height = it 
                animationTime = 0f // Reset on height change
            }
        }

        // Results
        SimulationCard(title = stringResource(R.string.results_title)) {
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

    val resultAppliedForceTemplate = stringResource(R.string.result_applied_force)
    val resultFrictionForceTemplate = stringResource(R.string.result_friction_force)
    val resultNetForceTemplate = stringResource(R.string.result_net_force)
    val errorText = stringResource(R.string.error_generic)

    // Animation
    var isPlaying by remember { mutableStateOf(false) }
    var animationTime by remember { mutableStateOf(0f) }

    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            val startTime = System.nanoTime()
            val startAnimTime = animationTime
            while (true) {
                withFrameNanos { frameTimeNanos ->
                    val dt = (frameTimeNanos - startTime) / 1_000_000_000f
                    animationTime = startAnimTime + dt
                }
                if (!isPlaying) break
            }
        }
    }

    LaunchedEffect(mass, acceleration, friction) {
        val jsonStr = NativeLibrary.safeCalculateNewtonSecondLaw(mass.toDouble(), acceleration.toDouble(), friction.toDouble())
        try {
            val json = JSONObject(jsonStr)
            resultText = """
                $resultAppliedForceTemplate
                $resultFrictionForceTemplate
                $resultNetForceTemplate
            """.trimIndent().format(
                json.optDouble("appliedForce"),
                json.optDouble("frictionForce"),
                json.optDouble("netForce")
            )
        } catch (e: Exception) {
            resultText = errorText
        }
    }

    SimulationScreen(title = stringResource(R.string.module_newton), onBackClick = onBackClick) {
        SimulationCard(title = stringResource(R.string.simulation_title)) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier.fillMaxWidth().height(200.dp)
                ) {
                    NewtonVisualizer(acceleration = acceleration / 10f, time = animationTime)
                }
                
                // Controls
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = { isPlaying = !isPlaying }) {
                        Text(if (isPlaying) stringResource(R.string.btn_stop) else stringResource(R.string.btn_start))
                    }
                    Button(onClick = { 
                        isPlaying = false
                        animationTime = 0f 
                    }) {
                        Text(stringResource(R.string.btn_reset))
                    }
                }
            }
        }

        SimulationCard(title = stringResource(R.string.parameters_title)) {
            InputSlider(label = stringResource(R.string.param_mass), value = mass, range = 1f..100f) { mass = it }
            InputSlider(label = stringResource(R.string.param_acceleration), value = acceleration, range = 0f..20f) { acceleration = it }
            InputSlider(label = stringResource(R.string.param_friction), value = friction, range = 0f..1f) { friction = it }
        }

        SimulationCard(title = stringResource(R.string.results_title)) {
            ResultText(resultText)
        }
    }
}


@Composable
fun ProjectileScreen(onBackClick: () -> Unit) {
    var velocity by remember { mutableStateOf(50.0f) }
    var angle by remember { mutableStateOf(45.0f) }
    var resultText by remember { mutableStateOf("") }

    // Animation
    var isPlaying by remember { mutableStateOf(false) }
    var animationTime by remember { mutableStateOf(0f) }

    val resultFlightTimeTemplate = stringResource(R.string.result_flight_time)
    val resultMaxHeightTemplate = stringResource(R.string.result_max_height)
    val resultRangeTemplate = stringResource(R.string.result_range)
    val errorText = stringResource(R.string.error_generic)

    // Animation Loop
    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            val startTime = System.nanoTime()
            val startAnimTime = animationTime
            
            // Calculate total flight time once
            val rad = Math.toRadians(angle.toDouble())
            val vy = velocity * Math.sin(rad)
            val totalFlightTime = (2 * vy / 9.81).toFloat()

            while (true) {
                withFrameNanos { frameTimeNanos ->
                    val dt = (frameTimeNanos - startTime) / 1_000_000_000f
                    animationTime = startAnimTime + dt * 2.0f // 2x speed
                }

                if (animationTime >= totalFlightTime) {
                    animationTime = totalFlightTime
                    isPlaying = false
                    break
                }
            }
        }
    }

    // Static calculation for results (always show max values)
    LaunchedEffect(velocity, angle) {
        val jsonStr = NativeLibrary.safeCalculateProjectileMotion(velocity.toDouble(), angle.toDouble())
        try {
            val json = JSONObject(jsonStr)
            resultText = """
                $resultFlightTimeTemplate
                $resultMaxHeightTemplate
                $resultRangeTemplate
            """.trimIndent().format(
                json.optDouble("flightTime"),
                json.optDouble("maxHeight"),
                json.optDouble("range")
            )
        } catch (e: Exception) {
            resultText = errorText
        }
    }

    SimulationScreen(title = stringResource(R.string.module_projectile), onBackClick = onBackClick) {
        SimulationCard(title = stringResource(R.string.simulation_title)) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier.fillMaxWidth().height(200.dp)
                ) {
                    val rad = Math.toRadians(angle.toDouble())
                    val vy = velocity * Math.sin(rad)
                    val totalFlightTime = (2 * vy / 9.81).toFloat()
                    val progress = if (totalFlightTime > 0) (animationTime / totalFlightTime).coerceIn(0f, 1f) else 0f
                    
                    ProjectileVisualizer(angle = angle, progress = progress)
                }
                
                 // Controls
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = { isPlaying = !isPlaying }) {
                        Text(if (isPlaying) stringResource(R.string.btn_stop) else stringResource(R.string.btn_start))
                    }
                    Button(onClick = { 
                        isPlaying = false
                        animationTime = 0f 
                    }) {
                        Text(stringResource(R.string.btn_reset))
                    }
                }
            }
        }

        SimulationCard(title = stringResource(R.string.parameters_title)) {
            InputSlider(label = stringResource(R.string.param_velocity), value = velocity, range = 10f..100f) { velocity = it; animationTime = 0f }
            InputSlider(label = stringResource(R.string.param_angle), value = angle, range = 0f..90f) { angle = it; animationTime = 0f }
        }

        SimulationCard(title = stringResource(R.string.results_title)) {
            ResultText(resultText)
        }
    }
}

@Composable
fun PendulumScreen(onBackClick: () -> Unit) {
    var length by remember { mutableStateOf(2.0f) }
    var startAngle by remember { mutableStateOf(30.0f) }
    var resultText by remember { mutableStateOf("") }
    
    // Animation
    var isPlaying by remember { mutableStateOf(false) }
    var animationTime by remember { mutableStateOf(0f) }

    val resultPeriodTemplate = stringResource(R.string.result_period)
    val resultMaxVelocityTemplate = stringResource(R.string.result_max_velocity)
    val errorText = stringResource(R.string.error_generic)

    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            val startTime = System.nanoTime()
            val startAnimTime = animationTime
            while (true) {
                withFrameNanos { frameTimeNanos ->
                    val dt = (frameTimeNanos - startTime) / 1_000_000_000f
                    animationTime = startAnimTime + dt
                }
                // Pendulum doesn't stop automatically
                if (!isPlaying) break
            }
        }
    }

    LaunchedEffect(length, startAngle) {
        val jsonStr = NativeLibrary.safeCalculatePendulum(length.toDouble(), startAngle.toDouble())
        try {
            val json = JSONObject(jsonStr)
            resultText = """
                $resultPeriodTemplate
                $resultMaxVelocityTemplate
            """.trimIndent().format(
                json.optDouble("period"),
                json.optDouble("maxVelocity")
            )
        } catch (e: Exception) {
            resultText = errorText
        }
    }

    SimulationScreen(title = stringResource(R.string.module_pendulum), onBackClick = onBackClick) {
        SimulationCard(title = stringResource(R.string.simulation_title)) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier.fillMaxWidth().height(200.dp)
                ) {
                    // Calculate current angle based on SHM: theta(t) = theta_max * cos(sqrt(g/L)*t)
                    val gravity = 9.81
                    val omega = Math.sqrt(gravity / length.toDouble())
                    val currentAngle = (startAngle * Math.cos(omega * animationTime)).toFloat()
                    
                    PendulumVisualizer(angle = currentAngle)
                }
                
                // Controls
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = { isPlaying = !isPlaying }) {
                        Text(if (isPlaying) stringResource(R.string.btn_stop) else stringResource(R.string.btn_start))
                    }
                    Button(onClick = { 
                        isPlaying = false
                        animationTime = 0f 
                    }) {
                        Text(stringResource(R.string.btn_reset))
                    }
                }
            }
        }

        SimulationCard(title = stringResource(R.string.parameters_title)) {
            InputSlider(label = stringResource(R.string.param_length), value = length, range = 0.5f..5f) { length = it; animationTime = 0f }
            InputSlider(label = stringResource(R.string.param_start_angle), value = startAngle, range = 0f..90f) { startAngle = it; animationTime = 0f }
        }

        SimulationCard(title = stringResource(R.string.results_title)) {
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

    val resultPotentialTemplate = stringResource(R.string.result_potential)
    val resultKineticTemplate = stringResource(R.string.result_kinetic)
    val resultTotalTemplate = stringResource(R.string.result_total)
    val errorText = stringResource(R.string.error_generic)

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
                $resultPotentialTemplate
                $resultKineticTemplate
                $resultTotalTemplate
            """.trimIndent().format(pe, ke, total)
        } catch (e: Exception) {
            resultText = errorText
        }
    }

    SimulationScreen(title = stringResource(R.string.module_energy), onBackClick = onBackClick) {
        SimulationCard(title = stringResource(R.string.simulation_title)) {
            Column(modifier = Modifier.fillMaxWidth().height(200.dp)) {
                EnergyVisualizer(peRatio = peRatio, keRatio = keRatio)
            }
        }

        SimulationCard(title = stringResource(R.string.parameters_title)) {
            InputSlider(label = stringResource(R.string.param_mass), value = mass, range = 1f..50f) { mass = it }
            InputSlider(label = stringResource(R.string.param_height), value = height, range = 0f..50f) { height = it }
            InputSlider(label = stringResource(R.string.param_velocity), value = velocity, range = 0f..30f) { velocity = it }
        }

        SimulationCard(title = stringResource(R.string.results_title)) {
            ResultText(resultText)
        }
    }
}
