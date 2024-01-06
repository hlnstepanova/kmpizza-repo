plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    kotlin("plugin.serialization") version Versions.KOTLIN_VERSION
    id("com.squareup.sqldelight")
}

version = "1.0"

kotlin {
    androidTarget()
    jvm()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
        }
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
        }
        commonMain.dependencies {
            // Ktor
            implementation(Versions.Common.KTOR_CLIENT_CORE)
            implementation(Versions.Common.KTOR_LOGGING)
            implementation(Versions.Common.KTOR_CLIENT_SERIALIZATION)
            implementation(Versions.Common.KTOR_CLIENT_CONTENT_NEGOTIATION)

            // Koin
            implementation(Versions.Common.KOIN_CORE)

            // Coroutines
            implementation(Versions.Common.COROUTINES) {
                version {
                    strictly(Versions.COROUTINES_MT)
                }
            }

            // SQLDelight
            implementation(Versions.Common.SQLDELIGHT_DRIVER)
        }

        commonTest.dependencies {
            implementation(kotlin("test-common"))
            implementation(kotlin("test-annotations-common"))
        }

        androidMain.dependencies {
            implementation(Versions.Android.KTOR_CLIENT)
            implementation(Versions.Android.KTOR_OKHTTP)
            implementation(Versions.Android.VIEW_MODEL)

            implementation(Versions.Android.SQLDELIGHT_DRIVER)
        }

        androidNativeTest.dependencies {
            implementation(kotlin("test-junit"))
            implementation("junit:junit:4.13.2")
        }

        iosMain.dependencies {
            implementation(Versions.iOS.KTOR_CLIENT)
            implementation(Versions.iOS.SQLDELIGHT_DRIVER)
        }
    }
}

android {
    compileSdk = 34
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 34
    }
    namespace = "dev.tutorial.kmpizza"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

sqldelight {
    database("KmpizzaDatabase") { // This will be the name of the generated database class.
        packageName = "dev.tutorial.kmpizza.db"
        sourceFolders = listOf("sqldelight")
    }
}
