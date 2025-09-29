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

/**
 * Serbest düşüş hesaplaması - Kullanıcı dostu fizik simülasyonu
 */
extern "C" JNIEXPORT jstring JNICALL
Java_com_yusufyorunc_fizik_simulator_NativeLibrary_onSpeedCardClicked(JNIEnv *env, jobject thiz) {
    LOGI("Hız kartı tıklandı - Serbest düşüş hesaplaması başlıyor...");

    // Serbest düşüş parametreleri
    double gravity = 9.81; // m/s² (Dünya'nın çekim ivmesi)
    double time = 3.0;     // saniye (düşüş süresi)
    double initialHeight = 50.0; // metre (başlangıç yüksekliği)

    // Fizik hesaplamaları
    double finalVelocity = gravity * time; // v = gt
    double distanceFallen = 0.5 * gravity * time * time; // s = 1/2 * g * t²
    double remainingHeight = initialHeight - distanceFallen;

    // Sonuçları formatlama
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

/**
 * Newton'un İkinci Yasası hesaplaması - F = ma
 */
extern "C" JNIEXPORT jstring JNICALL
Java_com_yusufyorunc_fizik_simulator_NativeLibrary_onForceCardClicked(JNIEnv *env, jobject thiz) {
    LOGI("Kuvvet kartı tıklandı - Newton'un İkinci Yasası hesaplaması...");

    // Fizik parametreleri
    double mass = 75.0;        // kg (ortalama insan ağırlığı)
    double acceleration = 2.5; // m/s² (ivme)
    double friction = 0.3;     // sürtünme katsayısı

    // Kuvvet hesaplamaları
    double appliedForce = mass * acceleration; // F = ma
    double weightForce = mass * 9.81;         // Ağırlık kuvveti
    double frictionForce = friction * weightForce; // Sürtünme kuvveti
    double netForce = appliedForce - frictionForce; // Net kuvvet

    // Sonuçları formatlama
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

/**
 * Sistem performans analizi
 */
extern "C" JNIEXPORT jstring JNICALL
Java_com_yusufyorunc_fizik_simulator_NativeLibrary_onDescriptionCardClicked(JNIEnv *env,
                                                                            jobject thiz) {
    LOGI("Sistem kartı tıklandı - Performans analizi başlıyor...");

    // Sistem metrikleri simülasyonu
    double cpuUsage = 23.7;      // % CPU kullanımı
    double memoryUsage = 156.8;  // MB RAM kullanımı
    double temperature = 42.3;   // °C işlemci sıcaklığı
    double batteryLevel = 87.5;  // % batarya seviyesi

    // Sistem durumu değerlendirmesi
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

    // Sonuçları formatlama
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
