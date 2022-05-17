package dev.tutorial.kmpizza.backend

import dev.tutorial.kmpizza.backend.di.getKoinModule
import dev.tutorial.kmpizza.backend.storage.exposed.LocalSource
import io.ktor.serialization.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin

internal fun Application.module() {
    install(Koin) {
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

    install(ContentNegotiation) {
        json()
    }

}


