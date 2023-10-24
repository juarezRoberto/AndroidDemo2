// Top-level build file where you can add configuration options common to all sub-projects/modules.
//buildscript {
//
//    repositories {
//        google()
//        mavenCentral()
//    }
//    dependencies {
//        classpath("com.android.tools.build:gradle:8.1.1")
//        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
//        classpath("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:1.9.0-1.0.13")
//        classpath("com.google.gms:google-services:4.4.0")
//        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48")
//        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.9")
//        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.6.0")

// NOTE: Do not place your application dependencies here; they belong
// in the individual module build.gradle files
//    }
//}


//tasks.register("clean", Delete::class) {
//    delete(rootProject.buildDir)
//}

plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    id("com.google.firebase.crashlytics") version "2.9.9" apply false
    id("androidx.navigation.safeargs") version "2.6.0" apply false
}