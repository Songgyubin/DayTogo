// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.2")
        classpath ("com.google.gms:google-services:4.3.13")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Dependencies.Kotlin.VERSION}")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:${Dependencies.Hilt.VERSION}")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}