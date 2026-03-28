plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.titoycode.budgetikomobile"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.titoycode.budgetikomobile"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.10.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.compose.ui:ui:1.4.2")
    implementation("androidx.compose.material:material:1.4.2")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.2")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.4.2")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
}
