package com.yusufyorunc.fizik.simulator

import android.util.Log

object NativeLibrary {

    private const val TAG = "FizikSimulator"

    init {
        try {
            System.loadLibrary("fizik_simulator")
            Log.d(TAG, "Native library loaded successfully")
        } catch (e: UnsatisfiedLinkError) {
            Log.e(TAG, "Failed to load native library: ${e.message}")
        }
    }

    external fun onSpeedCardClicked(): String

    external fun onForceCardClicked(): String

    external fun onDescriptionCardClicked(): String

    external fun stringFromJNI(): String
}
