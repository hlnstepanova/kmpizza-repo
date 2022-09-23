package dev.tutorial.kmpizza.api

import dev.tutorial.kmpizza.model.RecipeRequest
import dev.tutorial.kmpizza.model.RecipeResponse
import dev.tutorial.kmpizza.util.ImageFile
import dev.tutorial.kmpizza.util.toByteArray
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.utils.io.core.*

class RecipesApi(private val ktorApi: KtorApi) : KtorApi by ktorApi {
    companion object {
        const val RECIPES_BASE_URL = "recipes"
        const val IMAGE_FILE_PART = "image"
    }

    suspend fun getPizza(): String {
        return client.get {
            apiUrl("pizza")
        }.body()
    }

    suspend fun getRecipes(): List<RecipeResponse> {
        return client.get {
            apiUrl(RECIPES_BASE_URL)
        }.body()
    }

    suspend fun getRecipe(id: Long): RecipeResponse {
        return client.get {
            apiUrl("$RECIPES_BASE_URL/$id")
        }.body()
    }

    suspend fun postRecipe(recipeRequest: RecipeRequest): Long? {
        try {
            return client.post {
                json()
                apiUrl(RECIPES_BASE_URL)
                setBody(recipeRequest)
            }.body()
        } catch (e: Exception) {
            return null
        }
    }

    suspend fun postImage(recipeId: Long, icon: ImageFile): Unit = client.submitFormWithBinaryData(
        formData {
            appendInput(key = IMAGE_FILE_PART, headers = Headers.build {
                append(HttpHeaders.ContentDisposition, "filename=${recipeId}_image")
            }) {
                buildPacket { writeFully(icon.toByteArray()) }
            }
        }) {
        apiUrl("$RECIPES_BASE_URL/${recipeId}/recipeImage")
    }
        .body()
}
