import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

application {
    mainClass.set("dev.tutorial.kmpizza.backend.ServerKt")
}

plugins {
    kotlin("jvm")
    application
    id("com.github.johnrengelman.shadow") version Versions.SHADOW_JAR_VERSION
    kotlin("plugin.serialization") version Versions.KOTLIN_VERSION
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":shared"))

    // Ktor
    implementation(Versions.Jvm.KTOR_CLIENT_APACHE)
    implementation(Versions.Jvm.KTOR_SERIALIZATION)
    implementation(Versions.Jvm.KTOR_SERVER_CONTENT_NEGOTIATION)
    implementation(Versions.Jvm.KTOR_SERVER_CALL_LOGGING)
    implementation(Versions.Jvm.KTOR_STATUS_PAGES)
    implementation(Versions.Jvm.KTOR_SERVER_NETTY)
    implementation(Versions.Jvm.SLF4J)
    implementation(Versions.Jvm.KTOR_AUTH)
    implementation(Versions.Jvm.KTOR_WEB_SOCKETS)
    implementation(Versions.Jvm.KTOR_CLIENT_APACHE)

    // Exposed
    implementation(Versions.Jvm.JETBRAINS_EXPOSED_CORE)
    implementation(Versions.Jvm.JETBRAINS_EXPOSED_DAO)
    implementation(Versions.Jvm.JETBRAINS_EXPOSED_JDBC)

    // PostgreSQL
    implementation(Versions.Jvm.POSTGRESQL)
    implementation(Versions.Jvm.HIKARI_CONNECTION_POOL)

    // Koin
    implementation(Versions.Jvm.KOIN_KTOR)
    implementation(Versions.Jvm.KOIN_KTOR_LOGGER)

    // AWS
    implementation(platform(Versions.Jvm.AWS_JAVA_SDK))
    implementation(Versions.Jvm.AWS_S3)

}

tasks.withType<ShadowJar> {
    archiveBaseName.set("Backend")
    archiveClassifier.set("")
    archiveVersion.set("")
    isZip64 = true
}

