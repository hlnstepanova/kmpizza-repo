package dev.tutorial.kmpizza.backend.storage.exposed

internal interface LocalSource {
    suspend fun getPizza(): String
}