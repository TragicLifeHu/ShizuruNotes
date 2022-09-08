plugins {
    id("com.android.application")
    id("kotlin-android")
    id("androidx.navigation.safeargs")
    id("kotlin-kapt")
}

android {
    compileSdk = 33
    defaultConfig {
        applicationId = "com.github.nyanfantasia.shizurunotes"
        minSdk = 26
        targetSdk = 33
        versionCode = 81
        versionName = "1.17.2"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isShrinkResources = false
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            applicationIdSuffix = ".debug"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.0-beta01")
    implementation("androidx.core:core-ktx:1.9.0-rc01")
    implementation("com.google.android.material:material:1.8.0-alpha01")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.1")
    implementation("org.brotli:dec:0.1.2")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation("org.apache.commons:commons-compress:1.21")
    implementation(files("libs\\calendarview-3.7.1.aar"))
    androidTestImplementation("androidx.test.ext:junit:1.1.3")


    kapt("com.github.bumptech.glide:compiler:4.13.2")
    implementation("com.github.bumptech.glide:glide:4.13.2")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.10")
    implementation("com.google.code.gson:gson:2.9.1")
    implementation("androidx.preference:preference-ktx:1.2.0")
    implementation("com.google.guava:guava:31.1-jre")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")

    implementation("com.jakewharton:process-phoenix:2.1.2")
    implementation("com.github.lygttpod:SuperTextView:2.4.2")
    implementation("com.afollestad.material-dialogs:core:3.3.0")
    implementation("com.github.mancj:MaterialSearchBar:0.8.5")
    implementation("com.blankj:utilcodex:1.31.0")
}