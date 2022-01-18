package dev.tutorial.kmpizza.backend

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

internal fun Routing.api() {
    hello()
}

private fun Routing.hello() {
    get("/hello") {
        call.respondText("Hello!")
    }
}
