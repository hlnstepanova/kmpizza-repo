package dev.tutorial.kmpizza.backend.di

import dev.tutorial.kmpizza.backend.storage.aws.AmazonFileStorage
import dev.tutorial.kmpizza.backend.storage.aws.FileStorage
import dev.tutorial.kmpizza.backend.storage.exposed.LocalSource
import dev.tutorial.kmpizza.backend.storage.exposed.LocalSourceImpl
import io.ktor.application.*
import org.koin.dsl.module

internal fun Application.getKoinModule() = module {
        single<FileStorage> { AmazonFileStorage() }
        single<LocalSource> { LocalSourceImpl(get(), this@getKoinModule) }
}
