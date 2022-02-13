package dev.tutorial.kmpizza.backend

import dev.tutorial.kmpizza.backend.storage.exposed.LocalSource
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

internal fun Routing.api(localSource: LocalSource) {
    pizza(localSource)
}

private fun Routing.pizza(localSource: LocalSource) {
    get("/pizza") {
        localSource.getPizza().let {
            call.respond(it)
        }
    }
}

