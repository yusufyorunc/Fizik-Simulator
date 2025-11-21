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
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun FreeFallVisualizer(
    progress: Float,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        drawRect(
            color = Color(0xFF00E676),
            topLeft = Offset(0f, canvasHeight - 20f),
            size = Size(canvasWidth, 20f)
        )

        val ballRadius = 30f
        val startY = 50f
        val endY = canvasHeight - 20f - ballRadius
        val currentY = startY + (endY - startY) * progress

        drawCircle(
            color = Color(0xFF2979FF),
            radius = ballRadius,
            center = Offset(canvasWidth / 2, currentY)
        )
    }
}

@Composable
fun NewtonVisualizer(
    acceleration: Float,
    time: Float,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val centerY = canvasHeight / 2

        drawLine(
            color = Color.Gray,
            start = Offset(0f, centerY + 50f),
            end = Offset(canvasWidth, centerY + 50f),
            strokeWidth = 5f
        )

        val displacement = 0.5f * acceleration * time * time * 50f
        val startX = 50f
        val boxSize = 100f
        val currentX = (startX + displacement) % (canvasWidth - boxSize)

        drawRect(
            color = Color(0xFFFFEA00),
            topLeft = Offset(currentX, centerY - 50f),
            size = Size(boxSize, boxSize)
        )

        val arrowLength = 100f + (acceleration * 50f)
        drawLine(
            color = Color(0xFFFF1744),
            start = Offset(currentX + boxSize, centerY),
            end = Offset(currentX + boxSize + arrowLength, centerY),
            strokeWidth = 8f
        )
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
    progress: Float,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val startX = 50f
        val startY = canvasHeight - 50f

        val maxRange = canvasWidth - 100f
        val maxHeight = canvasHeight * 0.6f

        val path = Path()
        path.moveTo(startX, startY)

        val rad = Math.toRadians(angle.toDouble())
        val cosA = cos(rad).toFloat()
        val sinA = sin(rad).toFloat()

        val controlX = startX + (maxRange / 2)
        val controlY =
            startY - maxHeight * (sinA / 0.707f)
        val endX = startX + maxRange * cosA

        path.quadraticTo(controlX, controlY, endX, startY)

        drawPath(
            path = path,
            color = Color(0xFFD500F9).copy(alpha = 0.3f),
            style = Stroke(width = 5f)
        )

        drawLine(
            color = Color.White,
            start = Offset(startX, startY),
            end = Offset(startX + 60f * cosA, startY - 60f * sinA),
            strokeWidth = 15f
        )

        val currentX = startX + (endX - startX) * progress
        val heightFactor = 4 * progress * (1 - progress)
        val currentY = startY - (maxHeight * (sinA / 0.707f) * heightFactor)

        if (progress > 0) {
            drawCircle(
                color = Color(0xFFD500F9),
                radius = 15f,
                center = Offset(currentX, currentY)
            )
        }
    }
}

@Composable
fun PendulumVisualizer(
    angle: Float,
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

        drawLine(
            color = Color.White,
            start = Offset(pivotX, pivotY),
            end = Offset(ballX, ballY),
            strokeWidth = 3f
        )

        drawCircle(
            color = Color(0xFF00E5FF),
            radius = 40f,
            center = Offset(ballX, ballY)
        )
    }
}

@Composable
fun EnergyVisualizer(
    peRatio: Float,
    keRatio: Float,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val barWidth = size.width / 3
        val maxHeight = size.height * 0.8f
        val startY = size.height - 20f

        drawRect(
            color = Color(0xFFFF3D00),
            topLeft = Offset(size.width / 4 - barWidth / 2, startY - (maxHeight * peRatio)),
            size = Size(barWidth, maxHeight * peRatio)
        )

        drawRect(
            color = Color(0xFF76FF03),
            topLeft = Offset(size.width * 3 / 4 - barWidth / 2, startY - (maxHeight * keRatio)),
            size = Size(barWidth, maxHeight * keRatio)
        )
    }
}
