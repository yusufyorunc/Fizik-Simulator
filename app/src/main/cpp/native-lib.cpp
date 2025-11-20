#include <jni.h>
#include <string>
#include <cmath>
#include <sstream>
#include <iomanip>

extern "C" JNIEXPORT jstring JNICALL
Java_com_yusufyorunc_fizik_simulator_NativeLibrary_calculateFreeFall(JNIEnv *env, jobject thiz, jdouble time, jdouble initialHeight) {
    double gravity = 9.81;
    double finalVelocity = gravity * time;
    double distanceFallen = 0.5 * gravity * time * time;
    double remainingHeight = std::max(0.0, initialHeight - distanceFallen);

    std::ostringstream result;
    result << "{";
    result << "\"time\": " << time << ",";
    result << "\"initialHeight\": " << initialHeight << ",";
    result << "\"finalVelocity\": " << finalVelocity << ",";
    result << "\"distanceFallen\": " << distanceFallen << ",";
    result << "\"remainingHeight\": " << remainingHeight;
    result << "}";

    return env->NewStringUTF(result.str().c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_yusufyorunc_fizik_simulator_NativeLibrary_calculateNewtonSecondLaw(JNIEnv *env, jobject thiz, jdouble mass, jdouble acceleration, jdouble frictionCoefficient) {
    double appliedForce = mass * acceleration;
    double weightForce = mass * 9.81;
    double frictionForce = frictionCoefficient * weightForce;
    double netForce = appliedForce - frictionForce;

    std::ostringstream result;
    result << "{";
    result << "\"mass\": " << mass << ",";
    result << "\"acceleration\": " << acceleration << ",";
    result << "\"appliedForce\": " << appliedForce << ",";
    result << "\"frictionForce\": " << frictionForce << ",";
    result << "\"netForce\": " << netForce;
    result << "}";

    return env->NewStringUTF(result.str().c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_yusufyorunc_fizik_simulator_NativeLibrary_calculateEnergy(JNIEnv *env, jobject thiz, jdouble mass, jdouble height, jdouble velocity) {
    double gravity = 9.81;
    double potentialEnergy = mass * gravity * height;
    double kineticEnergy = 0.5 * mass * velocity * velocity;
    double totalEnergy = potentialEnergy + kineticEnergy;

    std::ostringstream result;
    result << "{";
    result << "\"mass\": " << mass << ",";
    result << "\"potentialEnergy\": " << potentialEnergy << ",";
    result << "\"kineticEnergy\": " << kineticEnergy << ",";
    result << "\"totalEnergy\": " << totalEnergy;
    result << "}";

    return env->NewStringUTF(result.str().c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_yusufyorunc_fizik_simulator_NativeLibrary_calculateProjectileMotion(JNIEnv *env, jobject thiz, jdouble velocity, jdouble angleDeg) {
    double gravity = 9.81;
    double angleRad = angleDeg * M_PI / 180.0;
    double vX = velocity * cos(angleRad);
    double vY = velocity * sin(angleRad);
    
    double flightTime = (2 * vY) / gravity;
    double maxHeight = (vY * vY) / (2 * gravity);
    double range = vX * flightTime;

    std::ostringstream result;
    result << "{";
    result << "\"flightTime\": " << flightTime << ",";
    result << "\"maxHeight\": " << maxHeight << ",";
    result << "\"range\": " << range;
    result << "}";

    return env->NewStringUTF(result.str().c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_yusufyorunc_fizik_simulator_NativeLibrary_calculatePendulum(JNIEnv *env, jobject thiz, jdouble length, jdouble angleDeg) {
    double gravity = 9.81;
    double period = 2 * M_PI * sqrt(length / gravity);
    // Max velocity at the bottom
    // PE = mgh = mg(L - Lcos(theta))
    // KE = 1/2 mv^2
    // v = sqrt(2gL(1-cos(theta)))
    double angleRad = angleDeg * M_PI / 180.0;
    double maxVelocity = sqrt(2 * gravity * length * (1 - cos(angleRad)));

    std::ostringstream result;
    result << "{";
    result << "\"period\": " << period << ",";
    result << "\"maxVelocity\": " << maxVelocity;
    result << "}";

    return env->NewStringUTF(result.str().c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_yusufyorunc_fizik_simulator_NativeLibrary_stringFromJNI(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF("Fizik Simulator Core Ready");
}