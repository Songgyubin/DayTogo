import Dependencies.applyAndroidX
import Dependencies.applyCoroutines
import Dependencies.applyFirebase
import Dependencies.applyHilt
import Dependencies.applyTest

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdk = Dependencies.COMPILE_SDK

    defaultConfig {
        minSdk = Dependencies.MIN_SDK
        targetSdk = Dependencies.TARGET_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":domain"))

    applyAndroidX()
    applyTest()
    applyFirebase()
    applyHilt()
    applyCoroutines()
}