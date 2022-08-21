import Dependencies.applyRx

plugins {
    id("com.android.library")
    id("kotlin-android")
}
android {
    compileSdk = Dependencies.COMPILE_SDK

    defaultConfig {
        minSdk = Dependencies.MIN_SDK
        targetSdk = Dependencies.TARGET_SDK
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    applyRx()
}
