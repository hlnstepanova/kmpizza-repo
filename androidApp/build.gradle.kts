plugins {
    id("com.android.application")
    kotlin("android")
}

dependencies {
    implementation(project(":shared"))

    implementation(Versions.Android.COMPOSE_UI)
    implementation(Versions.Android.COMPOSE_GRAPHICS)
    implementation(Versions.Android.COMPOSE_TOOLING)
    implementation(Versions.Android.COMPOSE_FOUNDATION)
    implementation(Versions.Android.COMPOSE_MATERIAL)
    implementation(Versions.Android.COMPOSE_NAVIGATION)
    implementation(Versions.Android.COMPOSE_ACTIVITY)
    implementation(Versions.Android.COMPOSE_COIL)

    implementation(Versions.Android.MATERIAL_COMPONENTS)

    implementation(Versions.Android.KOIN_ANDROID_MAIN)
}

android {
    compileSdk = 33
    defaultConfig {
        applicationId = "dev.tutorial.kmpizza.android"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
}
