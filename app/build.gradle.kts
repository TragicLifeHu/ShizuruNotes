plugins {
    id("com.android.application")
    id("kotlin-android")
    id("androidx.navigation.safeargs")
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    compileSdk = 34
    defaultConfig {
        applicationId = "com.github.nyanfantasia.shizurunotes"
        minSdk = 28
        targetSdk = 34
        versionCode = 106
        versionName = "1.27.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isShrinkResources = false
            proguardFiles("proguard-rules.pro")
        }
        debug {
            applicationIdSuffix = ".debug"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        dataBinding = true
        buildConfig = true
    }
    namespace = "com.github.nyanfantasia.shizurunotes"
}
kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("org.brotli:dec:0.1.2")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("org.apache.commons:commons-compress:1.26.1")
    implementation(files("libs/calendarview-3.7.1.aar"))
    androidTestImplementation("androidx.test.ext:junit:1.1.5")

    ksp("com.github.bumptech.glide:ksp:4.16.0")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("androidx.preference:preference-ktx:1.2.1")
    implementation("com.google.guava:guava:32.1.3-jre")
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    implementation("com.jakewharton:process-phoenix:3.0.0")
    implementation("com.github.lygttpod:SuperTextView:2.4.2")
    implementation("com.afollestad.material-dialogs:core:3.3.0")
    implementation("com.github.mancj:MaterialSearchBar:0.8.5")
    implementation("com.blankj:utilcodex:1.31.1")
}