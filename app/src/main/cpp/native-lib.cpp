#include <jni.h>
#include <string>
#include <android/log.h>
#include <cmath>
#include <sstream>
#include <iomanip>

#define LOG_TAG "FizikSimulator"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)

extern "C" JNIEXPORT jstring JNICALL
Java_com_yusufyorunc_fizik_simulator_NativeLibrary_stringFromJNI(JNIEnv *env, jobject thiz) {
    LOGI("Hoş geldin mesajı istendi");

    std::ostringstream welcome;
    welcome << "🎯 Fizik Simülatörüne Hoş Geldiniz! 🎯\n";
    welcome << "Kartlara tıklayarak fizik hesaplamalarını keşfedin!";

    LOGD("Hoş geldin mesajı hazırlandı");
    return env->NewStringUTF(welcome.str().c_str());
}


extern "C" JNIEXPORT jstring JNICALL
Java_com_yusufyorunc_fizik_simulator_NativeLibrary_onSpeedCardClicked(JNIEnv *env, jobject thiz) {
    LOGI("Hız kartı tıklandı - Serbest düşüş hesaplaması başlıyor...");

    double gravity = 9.81;
    double time = 5.0;
    double initialHeight = 50.0;

    double finalVelocity = gravity * time;
    double distanceFallen = 0.5 * gravity * time * time;
    double remainingHeight = initialHeight - distanceFallen;

    std::ostringstream result;
    result << std::fixed << std::setprecision(2);
    result << "🏗️ Serbest Düşüş Analizi:\n\n";
    result << "⏱️ Süre: " << time << " saniye\n";
    result << "📏 Başlangıç: " << initialHeight << " m\n";
    result << "⚡ Son Hız: " << finalVelocity << " m/s\n";
    result << "📉 Düşülen Mesafe: " << distanceFallen << " m\n";
    result << "📍 Kalan Yükseklik: " << remainingHeight << " m";

    LOGD("Hesaplama tamamlandı: Son hız = %.2f m/s", finalVelocity);

    return env->NewStringUTF(result.str().c_str());
}


extern "C" JNIEXPORT jstring JNICALL
Java_com_yusufyorunc_fizik_simulator_NativeLibrary_onForceCardClicked(JNIEnv *env, jobject thiz) {
    LOGI("Kuvvet kartı tıklandı - Newton'un İkinci Yasası hesaplaması...");

    double mass = 75.0;
    double acceleration = 2.5;
    double friction = 0.3;

    double appliedForce = mass * acceleration;
    double weightForce = mass * 9.81;
    double frictionForce = friction * weightForce;
    double netForce = appliedForce - frictionForce;

    std::ostringstream result;
    result << std::fixed << std::setprecision(2);
    result << "⚖️ Kuvvet Analizi (Newton'un 2. Yasası):\n\n";
    result << "👤 Kütle: " << mass << " kg\n";
    result << "🚀 İvme: " << acceleration << " m/s²\n";
    result << "💪 Uygulanan Kuvvet: " << appliedForce << " N\n";
    result << "🔥 Sürtünme Kuvveti: " << frictionForce << " N\n";
    result << "⚡ Net Kuvvet: " << netForce << " N";

    LOGD("Kuvvet hesaplaması: F = %.2f N", appliedForce);

    return env->NewStringUTF(result.str().c_str());
}


extern "C" JNIEXPORT jstring JNICALL
Java_com_yusufyorunc_fizik_simulator_NativeLibrary_onDescriptionCardClicked(JNIEnv *env,
                                                                            jobject thiz) {
    LOGI("Sistem kartı tıklandı - Performans analizi başlıyor...");

    double cpuUsage = 23.7;
    double memoryUsage = 156.8;
    double temperature = 42.3;
    double batteryLevel = 87.5;

    std::string status;
    std::string emoji;

    if (temperature < 50 && cpuUsage < 30 && batteryLevel > 50) {
        status = "Mükemmel";
        emoji = "🟢";
    } else if (temperature < 70 && cpuUsage < 60 && batteryLevel > 20) {
        status = "İyi";
        emoji = "🟡";
    } else {
        status = "Dikkat";
        emoji = "🔴";
    }

    std::ostringstream result;
    result << std::fixed << std::setprecision(1);
    result << "📊 Sistem Performans Raporu:\n\n";
    result << "🧠 CPU Kullanımı: " << cpuUsage << "%\n";
    result << "💾 RAM Kullanımı: " << memoryUsage << " MB\n";
    result << "🌡️ Sıcaklık: " << temperature << "°C\n";
    result << "🔋 Batarya: " << batteryLevel << "%\n\n";
    result << emoji << " Durum: " << status;

    LOGD("Sistem analizi tamamlandı: %s", status.c_str());

    return env->NewStringUTF(result.str().c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_yusufyorunc_fizik_simulator_NativeLibrary_OnEnergyCardClicked(JNIEnv *env, jobject thiz) {
    LOGI("Enerji kartı tıklandı - Enerji dönüşümü hesaplaması...");

    double mass = 10.0;
    double height = 20.0;
    double velocity = 15.0;
    double gravity = 9.81;

    double potentialEnergy = mass * gravity * height;
    double kineticEnergy = 0.5 * mass * velocity * velocity;
    double totalEnergy = potentialEnergy + kineticEnergy;

    std::ostringstream result;
    result << std::fixed << std::setprecision(2);
    result << "🔋 Enerji Dönüşümü Analizi:\n\n";
    result << "📦 Kütle: " << mass << " kg\n";
    result << "📏 Yükseklik: " << height << " m\n";
    result << "🚀 Hız: " << velocity << " m/s\n";
    result << "⚡ Potansiyel Enerji: " << potentialEnergy << " J\n";
    result << "⚡ Kinetik Enerji: " << kineticEnergy << " J\n";
    result << "⚡ Toplam Enerji: " << totalEnergy << " J";

    LOGD("Enerji hesaplaması tamamlandı: Toplam Enerji = %.2f J", totalEnergy);

    return env->NewStringUTF(result.str().c_str());
}