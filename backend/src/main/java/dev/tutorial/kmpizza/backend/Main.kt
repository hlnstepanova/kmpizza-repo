package dev.tutorial.kmpizza.backend

import dev.tutorial.kmpizza.backend.di.getKoinModule
import dev.tutorial.kmpizza.backend.storage.exposed.LocalSource
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject

internal fun Application.module() {
    install(Koin) {
        modules(getKoinModule())
    }

    val localSource by inject<LocalSource>()

    install(Routing) {
        api(localSource)
    }

    install(StatusPages) {
        exception<Throwable> { cause ->
            call.respond(cause.toString())
        }
    }

    install(ContentNegotiation) {
        json()
    }

}


