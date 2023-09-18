plugins {
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
}
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48")
        classpath("com.android.tools.build:gradle:8.1.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
    }
}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}