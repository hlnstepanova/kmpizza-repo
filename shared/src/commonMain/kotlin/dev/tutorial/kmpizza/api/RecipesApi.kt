package dev.tutorial.kmpizza.api

import dev.tutorial.kmpizza.model.Recipe
import dev.tutorial.kmpizza.model.RecipeResponse
import io.ktor.client.features.*
import io.ktor.client.features.get
import io.ktor.client.request.*
import kotlin.collections.get

class RecipesApi(private val ktorApi: KtorApi) : KtorApi by ktorApi {
    companion object {
        const val RECIPES_BASE_URL = "recipes"
    }

    suspend fun getPizza(): String {
        return client.get {
            apiUrl("pizza")
        }
    }

    suspend fun getRecipes(): List<RecipeResponse> {
        return client.get {
            apiUrl(RECIPES_BASE_URL)
        }
    }

    suspend fun getRecipe(id: Long): RecipeResponse {
        return client.get {
            apiUrl("$RECIPES_BASE_URL/$id")
        }
    }

    suspend fun postRecipe(recipe: Recipe): Long {
        return client.post {
            json()
            apiUrl(RECIPES_BASE_URL)
        }
    }

}
