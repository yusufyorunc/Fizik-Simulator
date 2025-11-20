package com.yusufyorunc.fizik.simulator.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun FreeFallVisualizer(
    progress: Float, // 0.0 to 1.0
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        
        // Ground
        drawRect(
            color = Color(0xFF00E676), // Neon Green
            topLeft = Offset(0f, canvasHeight - 20f),
            size = Size(canvasWidth, 20f)
        )

        // Ball
        val ballRadius = 30f
        val startY = 50f
        val endY = canvasHeight - 20f - ballRadius
        val currentY = startY + (endY - startY) * progress

        drawCircle(
            color = Color(0xFF2979FF), // Neon Blue
            radius = ballRadius,
            center = Offset(canvasWidth / 2, currentY)
        )
    }
}

@Composable
fun NewtonVisualizer(
    acceleration: Float, // relative scale
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val centerY = canvasHeight / 2

        // Track
        drawLine(
            color = Color.Gray,
            start = Offset(0f, centerY + 50f),
            end = Offset(canvasWidth, centerY + 50f),
            strokeWidth = 5f
        )

        // Box
        val boxSize = 100f
        val boxX = (canvasWidth - boxSize) / 2
        
        drawRect(
            color = Color(0xFFFFEA00), // Neon Yellow
            topLeft = Offset(boxX, centerY - 50f),
            size = Size(boxSize, boxSize)
        )

        // Force Arrow
        val arrowLength = 100f + (acceleration * 50f)
        drawLine(
            color = Color(0xFFFF1744), // Neon Red
            start = Offset(boxX + boxSize, centerY),
            end = Offset(boxX + boxSize + arrowLength, centerY),
            strokeWidth = 8f
        )
        // Arrow head
        drawLine(
            color = Color(0xFFFF1744),
            start = Offset(boxX + boxSize + arrowLength - 20f, centerY - 20f),
            end = Offset(boxX + boxSize + arrowLength, centerY),
            strokeWidth = 8f
        )
        drawLine(
            color = Color(0xFFFF1744),
            start = Offset(boxX + boxSize + arrowLength - 20f, centerY + 20f),
            end = Offset(boxX + boxSize + arrowLength, centerY),
            strokeWidth = 8f
        )
    }
}

@Composable
fun ProjectileVisualizer(
    angle: Float,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val startX = 50f
        val startY = canvasHeight - 50f

        // Trajectory
        val path = Path()
        path.moveTo(startX, startY)
        
        // Simple parabolic curve approximation for visual
        val controlX = startX + (canvasWidth / 2) * cos(Math.toRadians(angle.toDouble())).toFloat()
        val controlY = startY - (canvasHeight * sin(Math.toRadians(angle.toDouble())).toFloat())
        val endX = canvasWidth - 50f
        
        path.quadraticBezierTo(controlX, controlY, endX, startY)

        drawPath(
            path = path,
            color = Color(0xFFD500F9), // Neon Purple
            style = Stroke(width = 5f)
        )

        // Cannon
        drawLine(
            color = Color.White,
            start = Offset(startX, startY),
            end = Offset(startX + 60f * cos(Math.toRadians(angle.toDouble())).toFloat(), 
                        startY - 60f * sin(Math.toRadians(angle.toDouble())).toFloat()),
            strokeWidth = 15f
        )
    }
}

@Composable
fun PendulumVisualizer(
    angle: Float, // current angle in degrees
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val pivotX = canvasWidth / 2
        val pivotY = 50f
        val length = canvasHeight * 0.6f

        val ballX = pivotX + length * sin(Math.toRadians(angle.toDouble())).toFloat()
        val ballY = pivotY + length * cos(Math.toRadians(angle.toDouble())).toFloat()

        // String
        drawLine(
            color = Color.White,
            start = Offset(pivotX, pivotY),
            end = Offset(ballX, ballY),
            strokeWidth = 3f
        )

        // Bob
        drawCircle(
            color = Color(0xFF00E5FF), // Neon Cyan
            radius = 40f,
            center = Offset(ballX, ballY)
        )
    }
}

@Composable
fun EnergyVisualizer(
    peRatio: Float, // 0 to 1
    keRatio: Float, // 0 to 1
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val barWidth = size.width / 3
        val maxHeight = size.height * 0.8f
        val startY = size.height - 20f

        // PE Bar
        drawRect(
            color = Color(0xFFFF3D00), // Deep Orange
            topLeft = Offset(size.width / 4 - barWidth / 2, startY - (maxHeight * peRatio)),
            size = Size(barWidth, maxHeight * peRatio)
        )

        // KE Bar
        drawRect(
            color = Color(0xFF76FF03), // Light Green
            topLeft = Offset(size.width * 3 / 4 - barWidth / 2, startY - (maxHeight * keRatio)),
            size = Size(barWidth, maxHeight * keRatio)
        )
    }
}
