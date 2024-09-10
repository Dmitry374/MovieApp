import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.kotlin.serialization)
}

val localPropertiesFile = rootProject.file("local.properties")
val localProperties = Properties().apply {
    runCatching {
        localPropertiesFile.inputStream().use { fis ->
            load(fis)
        }
    }
}

android {
    namespace = "com.example.network"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        buildConfigField(
            "String",
            "API_KEY",
            "\"${localProperties["API_KEY"]}\""
        )
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
    implementation(libs.retrofit.converter.kotlinx)

    // logging
    debugImplementation(libs.okhttp.logging)

    // coroutines
    implementation(libs.kotlin.coroutines)

    // di
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.android.compiler)
}