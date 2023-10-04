plugins {
    id(Dependencies.Google.DevTools.Ksp.plugin) version Dependencies.Google.DevTools.Ksp.version apply false
}
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.Google.Dagger.Hilt.classpath)
        classpath(Dependencies.Gradle.classpath)
        classpath(Dependencies.Kotlin.classpath)
    }
}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}