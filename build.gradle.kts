// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val kotlin_version by extra("1.9.0")
    dependencies {
        classpath("com.google.gms:google-services:4.3.13")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")

        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
    }
    repositories {
        mavenCentral()
        maven ("https://jitpack.io")
    }
}

plugins {
    id("com.android.application") version "8.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.gms.google-services") version "4.3.13" apply false
}