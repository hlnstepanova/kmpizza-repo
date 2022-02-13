package dev.tutorial.kmpizza.backend.di

import dev.tutorial.kmpizza.backend.storage.exposed.LocalSource
import dev.tutorial.kmpizza.backend.storage.exposed.LocalSourceImpl
import io.ktor.application.*
import org.koin.dsl.module

internal fun Application.getKoinModule() = module {
    single<LocalSource> { LocalSourceImpl(this@getKoinModule) }
}
