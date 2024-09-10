plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.network"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(project(":core:common"))

    // retrofit
    api(libs.retrofit)
    api(libs.kotlin.serialization)

    // logging
    debugImplementation(libs.okhttp.logging)

    // coroutines
    implementation(libs.kotlin.coroutines)

    // di
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.android.compiler)
}