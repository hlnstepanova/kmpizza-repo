object Versions {
    const val KOTLIN_VERSION = "1.6.10"
    const val KTOR_VERSION = "1.6.7"
    const val SHADOW_JAR_VERSION = "7.1.1"
    const val EXPOSED_VERSION = "0.36.2"
    const val POSTGRESQL_VERSION = "42.3.1"
    const val HIKARI_VESRION = "5.0.0"
    const val KOIN_VERSION = "3.2.0-beta-1"
    const val AWS_VERSION = "2.17.102"

    object Jvm {
        const val KTOR_AUTH = "io.ktor:ktor-auth:$KTOR_VERSION"
        const val KTOR_WEB_SOCKETS = "io.ktor:ktor-websockets:$KTOR_VERSION"
        const val KTOR_CLIENT_APACHE = "io.ktor:ktor-client-apache:$KTOR_VERSION"
        const val KTOR_SERVER_NETTY = "io.ktor:ktor-server-netty:$KTOR_VERSION"
        const val KTOR_SERIALIZATION = "io.ktor:ktor-serialization:$KTOR_VERSION"
        const val JETBRAINS_EXPOSED_CORE = "org.jetbrains.exposed:exposed-core:$EXPOSED_VERSION"
        const val JETBRAINS_EXPOSED_DAO = "org.jetbrains.exposed:exposed-dao:$EXPOSED_VERSION"
        const val JETBRAINS_EXPOSED_JDBC = "org.jetbrains.exposed:exposed-jdbc:$EXPOSED_VERSION"
        const val HIKARI_CONNECTION_POOL = "com.zaxxer:HikariCP:$HIKARI_VESRION"
        const val POSTGRESQL = "org.postgresql:postgresql:$POSTGRESQL_VERSION"
        const val KOIN_KTOR = "io.insert-koin:koin-ktor:$KOIN_VERSION"
        const val AWS_JAVA_SDK = "software.amazon.awssdk:bom:$AWS_VERSION"
        const val AWS_S3 = "software.amazon.awssdk:s3"
    }

    object Common {
        const val KTOR_CLIENT_CORE = "io.ktor:ktor-client-core:$KTOR_VERSION"
        const val KTOR_CLIENT_JSON = "io.ktor:ktor-client-json:$KTOR_VERSION"
        const val KTOR_LOGGING = "io.ktor:ktor-client-logging:$KTOR_VERSION"
        const val KTOR_CLIENT_SERIALIZATION = "io.ktor:ktor-client-serialization:$KTOR_VERSION"
    }

    object Android {
        const val KTOR_CLIENT = "io.ktor:ktor-client-android:$KTOR_VERSION"
        const val KTOR_OKHTTP = "io.ktor:ktor-client-okhttp:$KTOR_VERSION"
    }

    object iOS {
        const val KTOR_CLIENT = "io.ktor:ktor-client-ios:$KTOR_VERSION"
    }

}
