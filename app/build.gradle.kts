import Dependencies.applyAndroidX
import Dependencies.applyCoroutines
import Dependencies.applyFirebase
import Dependencies.applyHilt
import Dependencies.applyNaver
import Dependencies.applyTest
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
}

android {
    compileSdk = Dependencies.COMPILE_SDK
    buildFeatures {
        dataBinding = true
    }

    // local.properties에서 키,값 가져오기
    val localProperties = Properties()
    localProperties.load(FileInputStream(rootProject.file("local.properties")))

    val naver_map_client_id = localProperties.getProperty("naver_map_client_id")
    val naver_map_client_id_manifest = localProperties.getProperty("naver_map_client_id_manifest")

    defaultConfig {
        applicationId = "gyul.songgyubin.daytogo"
        minSdk = Dependencies.MIN_SDK
        targetSdk = Dependencies.TARGET_SDK
        versionCode = 1
        versionName = "1.0"
        manifestPlaceholders["naverMapClientId"] = naver_map_client_id_manifest
        buildConfigField("String", "NaverMapClientId", naver_map_client_id)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    implementation(project(":common"))
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(Dependencies.Google.MATERIAL)
    applyAndroidX()
    applyTest()
    applyFirebase()
    applyNaver()
    applyHilt()
    applyCoroutines()
}
kapt {
    correctErrorTypes = true
}
hilt {
    enableTransformForLocalTests = true
}