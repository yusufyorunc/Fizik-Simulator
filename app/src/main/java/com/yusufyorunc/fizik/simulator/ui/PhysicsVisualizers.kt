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
    time: Float, // animation time
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

        // Box Animation
        // x = 1/2 * a * t^2
        // Scale factor for visual
        val displacement = 0.5f * acceleration * time * time * 50f 
        val startX = 50f
        val boxSize = 100f
        // Loop the animation if it goes off screen or just clamp
        val currentX = (startX + displacement) % (canvasWidth - boxSize)

        drawRect(
            color = Color(0xFFFFEA00), // Neon Yellow
            topLeft = Offset(currentX, centerY - 50f),
            size = Size(boxSize, boxSize)
        )

        // Force Arrow (moves with box)
        val arrowLength = 100f + (acceleration * 50f)
        drawLine(
            color = Color(0xFFFF1744), // Neon Red
            start = Offset(currentX + boxSize, centerY),
            end = Offset(currentX + boxSize + arrowLength, centerY),
            strokeWidth = 8f
        )
        // Arrow head
        drawLine(
            color = Color(0xFFFF1744),
            start = Offset(currentX + boxSize + arrowLength - 20f, centerY - 20f),
            end = Offset(currentX + boxSize + arrowLength, centerY),
            strokeWidth = 8f
        )
        drawLine(
            color = Color(0xFFFF1744),
            start = Offset(currentX + boxSize + arrowLength - 20f, centerY + 20f),
            end = Offset(currentX + boxSize + arrowLength, centerY),
            strokeWidth = 8f
        )
    }
}

@Composable
fun ProjectileVisualizer(
    angle: Float,
    progress: Float, // 0.0 to 1.0
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val startX = 50f
        val startY = canvasHeight - 50f
        
        // Max range visual scale (approximate)
        val maxRange = canvasWidth - 100f
        val maxHeight = canvasHeight * 0.6f

        // Trajectory Path
        val path = Path()
        path.moveTo(startX, startY)
        
        val rad = Math.toRadians(angle.toDouble())
        val cosA = cos(rad).toFloat()
        val sinA = sin(rad).toFloat()
        
        // Control point for quadratic bezier
        // This is a visual approximation. For true physics path we'd need many points.
        // But for "vibe" this is fine.
        val controlX = startX + (maxRange / 2)
        val controlY = startY - maxHeight * (sinA / 0.707f) // Scale height by angle relative to 45 deg
        val endX = startX + maxRange * cosA // Range depends on angle
        
        path.quadraticBezierTo(controlX, controlY, endX, startY)

        drawPath(
            path = path,
            color = Color(0xFFD500F9).copy(alpha = 0.3f), // Neon Purple, dim
            style = Stroke(width = 5f)
        )

        // Cannon
        drawLine(
            color = Color.White,
            start = Offset(startX, startY),
            end = Offset(startX + 60f * cosA, startY - 60f * sinA),
            strokeWidth = 15f
        )
        
        // Projectile Ball Animation
        // We calculate position based on progress (0..1) along the theoretical parabolic path
        // x = v0 * cos * t
        // y = v0 * sin * t - 0.5 * g * t^2
        // Normalized:
        // x_norm = progress
        // y_norm = 4 * progress * (1 - progress) for a parabola starting and ending at 0
        
        val currentX = startX + (endX - startX) * progress
        // Parabolic height factor: 4 * p * (1-p) peaks at p=0.5 with value 1
        // We scale this by our visual max height
        val heightFactor = 4 * progress * (1 - progress)
        val currentY = startY - (maxHeight * (sinA / 0.707f) * heightFactor)

        if (progress > 0) {
             drawCircle(
                color = Color(0xFFD500F9), // Neon Purple
                radius = 15f,
                center = Offset(currentX, currentY)
            )
        }
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
