plugins {
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
}

buildscript {
    val agpVersion by extra("8.1.2")
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:$agpVersion")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.10")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.3")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

task<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}
