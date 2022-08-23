import Dependencies.applyAndroidX
import Dependencies.applyRx
import Dependencies.applyTest
import Dependencies.applyFirebase
import Dependencies.applyNaver
import Dependencies.applyKakao
import Dependencies.applyHilt
import java.util.Properties
import java.io.FileInputStream

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
    val kakaoSdkKey = localProperties.getProperty("kakao_sdk_key")
    val kakao_sdk_key_manifest = localProperties.getProperty("kakao_sdk_key_manifest")

    defaultConfig {
        applicationId = "gyul.songgyubin.daytogo"
        minSdk = Dependencies.MIN_SDK
        targetSdk = Dependencies.TARGET_SDK
        versionCode = 1
        versionName = "1.0"
        manifestPlaceholders["naverMapClientId"] = naver_map_client_id_manifest
        manifestPlaceholders["kakaoSdkKeyManifest"] = kakao_sdk_key_manifest

        buildConfigField("String", "KakaoSdkKey", kakaoSdkKey)
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

    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(Dependencies.Google.MATERIAL)
    applyAndroidX()
    applyTest()
    applyRx()
    applyFirebase()
    applyNaver()
    applyKakao()
    applyHilt()
}
kapt {
    correctErrorTypes = true
}
hilt {
    enableTransformForLocalTests = true
}