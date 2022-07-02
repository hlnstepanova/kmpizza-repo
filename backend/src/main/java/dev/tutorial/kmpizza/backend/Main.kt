package dev.tutorial.kmpizza.backend

import dev.tutorial.kmpizza.backend.di.getKoinModule
import dev.tutorial.kmpizza.backend.storage.exposed.LocalSource
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import org.slf4j.event.Level

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

internal fun Application.module() {
    install(Koin) {
        slf4jLogger()
        modules(getKoinModule())
    }

    val localSource by inject<LocalSource>()

    install(Routing) {
        api(localSource)
    }

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respond(cause.toString())
        }
    }

    install(CallLogging) {
        level = Level.INFO
    }

    install(ContentNegotiation) {
        json()
    }

}


