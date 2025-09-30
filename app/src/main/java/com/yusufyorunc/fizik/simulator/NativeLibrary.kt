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

    external fun onSpeedCardClicked(): String

    external fun onForceCardClicked(): String

    external fun onDescriptionCardClicked(): String

    external fun OnEnergyCardClicked(): String

    external fun stringFromJNI(): String

    fun safeOnSpeedCardClicked(): String {
        return if (isLibraryLoaded) {
            try {
                onSpeedCardClicked()
            } catch (e: Exception) {
                Log.e(TAG, "Error in onSpeedCardClicked: ${e.message}")
                "Hız hesaplaması şu anda mevcut değil. Lütfen uygulamayı yeniden başlatın."
            }
        } else {
            "Native library yüklenemedi. Hız hesaplaması yapılamıyor."
        }
    }

    fun safeOnForceCardClicked(): String {
        return if (isLibraryLoaded) {
            try {
                onForceCardClicked()
            } catch (e: Exception) {
                Log.e(TAG, "Error in onForceCardClicked: ${e.message}")
                "Kuvvet hesaplaması şu anda mevcut değil. Lütfen uygulamayı yeniden başlatın."
            }
        } else {
            "Native library yüklenemedi. Kuvvet hesaplaması yapılamıyor."
        }
    }

    fun safeOnDescriptionCardClicked(): String {
        return if (isLibraryLoaded) {
            try {
                onDescriptionCardClicked()
            } catch (e: Exception) {
                Log.e(TAG, "Error in onDescriptionCardClicked: ${e.message}")
                "Sistem analizi şu anda mevcut değil. Lütfen uygulamayı yeniden başlatın."
            }
        } else {
            "Native library yüklenemedi. Sistem analizi yapılamıyor."
        }
    }

    fun safeOnEnergyCardClicked(): String {
        return if (isLibraryLoaded) {
            try {
                OnEnergyCardClicked()
            } catch (e: Exception) {
                Log.e(TAG, "Error in onEnergyCardClicked: ${e.message}")
                "Enerji hesaplaması şu anda mevcut değil. Lütfen uygulamayı yeniden başlatın."
            }
        } else {
            "Native library yüklenemedi. Enerji hesaplaması yapılamıyor."
        }
    }

    fun safeStringFromJNI(): String {
        return if (isLibraryLoaded) {
            try {
                stringFromJNI()
            } catch (e: Exception) {
                Log.e(TAG, "Error in stringFromJNI: ${e.message}")
                "🎯 Fizik Simülatörüne Hoş Geldiniz! 🎯\nKartlara tıklayarak fizik hesaplamalarını keşfedin!"
            }
        } else {
            "🎯 Fizik Simülatörüne Hoş Geldiniz! 🎯\nKartlara tıklayarak fizik hesaplamalarını keşfedin!"
        }
    }
}
