buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN_VERSION}")
        classpath("com.android.tools.build:gradle:7.2.0")
        classpath(Versions.Common.SQLDELIGHT_PLUGIN)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://dl.bintray.com/kotlin/exposed")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}