package dev.tutorial.kmpizza.api

import io.ktor.client.*
import io.ktor.client.request.*

interface KtorApi {
    val client: HttpClient
    fun HttpRequestBuilder.apiUrl(path: String)
    fun HttpRequestBuilder.json()
}
