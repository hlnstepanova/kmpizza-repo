package dev.tutorial.kmpizza.api

import dev.tutorial.kmpizza.model.Recipe
import dev.tutorial.kmpizza.model.RecipeResponse
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlin.collections.get

class RecipesApi(private val ktorApi: KtorApi) : KtorApi by ktorApi {
    companion object {
        const val RECIPES_BASE_URL = "recipes"
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

    suspend fun postRecipe(recipe: Recipe): Long {
        return client.post {
            json()
            apiUrl(RECIPES_BASE_URL)
        }.body()
    }

}
