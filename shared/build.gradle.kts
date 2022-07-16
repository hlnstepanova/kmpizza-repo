import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    kotlin("plugin.serialization") version Versions.KOTLIN_VERSION
}

version = "1.0"

kotlin {
    android()
    jvm()

    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget = when {
        System.getenv("SDK_NAME")?.startsWith("iphoneos") == true -> ::iosArm64
        System.getenv("NATIVE_ARCH")?.startsWith("arm") == true -> ::iosSimulatorArm64
        else -> ::iosX64
    }

    iosTarget("ios") {}

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
        val commonMain by getting {
            dependencies {
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
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(Versions.Android.KTOR_CLIENT)
                implementation(Versions.Android.KTOR_OKHTTP)
                implementation(Versions.Android.VIEW_MODEL)
            }
        }

        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }

        val iosMain by getting {
            dependencies {
                implementation(Versions.iOS.KTOR_CLIENT)
            }
        }

        val iosTest by getting
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 31
    }
}