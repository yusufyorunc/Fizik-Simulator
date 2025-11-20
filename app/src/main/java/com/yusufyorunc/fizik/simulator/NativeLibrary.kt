package com.yusufyorunc.fizik.simulator

import android.util.Log

object NativeLibrary {

    private const val TAG = "FizikSimulator"
    private var isLibraryLoaded = false

    init {
        try {
            System.loadLibrary("fizik_simulator")
            isLibraryLoaded = true
            Log.d(TAG, "Native library loaded successfully")
        } catch (e: UnsatisfiedLinkError) {
            isLibraryLoaded = false
            Log.e(TAG, "Failed to load native library: ${e.message}")
        }
    }

    external fun calculateFreeFall(time: Double, initialHeight: Double): String
    external fun calculateNewtonSecondLaw(mass: Double, acceleration: Double, frictionCoefficient: Double): String
    external fun calculateEnergy(mass: Double, height: Double, velocity: Double): String
    external fun calculateProjectileMotion(velocity: Double, angleDeg: Double): String
    external fun calculatePendulum(length: Double, angleDeg: Double): String
    external fun stringFromJNI(): String

    fun safeCalculateFreeFall(time: Double, initialHeight: Double): String {
        return if (isLibraryLoaded) {
            try {
                calculateFreeFall(time, initialHeight)
            } catch (e: Exception) {
                Log.e(TAG, "Error in calculateFreeFall: ${e.message}")
                "{}"
            }
        } else {
            "{}"
        }
    }

    fun safeCalculateNewtonSecondLaw(mass: Double, acceleration: Double, frictionCoefficient: Double): String {
        return if (isLibraryLoaded) {
            try {
                calculateNewtonSecondLaw(mass, acceleration, frictionCoefficient)
            } catch (e: Exception) {
                Log.e(TAG, "Error in calculateNewtonSecondLaw: ${e.message}")
                "{}"
            }
        } else {
            "{}"
        }
    }

    fun safeCalculateEnergy(mass: Double, height: Double, velocity: Double): String {
        return if (isLibraryLoaded) {
            try {
                calculateEnergy(mass, height, velocity)
            } catch (e: Exception) {
                Log.e(TAG, "Error in calculateEnergy: ${e.message}")
                "{}"
            }
        } else {
            "{}"
        }
    }

    fun safeCalculateProjectileMotion(velocity: Double, angleDeg: Double): String {
        return if (isLibraryLoaded) {
            try {
                calculateProjectileMotion(velocity, angleDeg)
            } catch (e: Exception) {
                Log.e(TAG, "Error in calculateProjectileMotion: ${e.message}")
                "{}"
            }
        } else {
            "{}"
        }
    }

    fun safeCalculatePendulum(length: Double, angleDeg: Double): String {
        return if (isLibraryLoaded) {
            try {
                calculatePendulum(length, angleDeg)
            } catch (e: Exception) {
                Log.e(TAG, "Error in calculatePendulum: ${e.message}")
                "{}"
            }
        } else {
            "{}"
        }
    }

    fun safeStringFromJNI(): String {
        return if (isLibraryLoaded) {
            try {
                stringFromJNI()
            } catch (e: Exception) {
                Log.e(TAG, "Error in stringFromJNI: ${e.message}")
                "Core Error"
            }
        } else {
            "Native Lib Error"
        }
    }
}
