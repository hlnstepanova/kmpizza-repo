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
    compileSdk = 34
    defaultConfig {
        applicationId = "dev.tutorial.kmpizza.android"
        minSdk = 21
        targetSdk = 34
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.5"
    }
    namespace = "dev.tutorial.kmpizza.android"
}
