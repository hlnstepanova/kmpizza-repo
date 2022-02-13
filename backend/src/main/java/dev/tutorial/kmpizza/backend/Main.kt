package dev.tutorial.kmpizza.backend

import dev.tutorial.kmpizza.backend.di.getKoinModule
import dev.tutorial.kmpizza.backend.storage.exposed.LocalSource
import io.ktor.application.*
import io.ktor.routing.*
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
}


