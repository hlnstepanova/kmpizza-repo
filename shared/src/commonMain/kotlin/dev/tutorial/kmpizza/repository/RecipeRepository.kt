package dev.tutorial.kmpizza.repository

import dev.tutorial.kmpizza.model.RecipeRequest
import dev.tutorial.kmpizza.model.RecipeResponse
import dev.tutorial.kmpizza.remote.RecipeRemoteSource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RecipeRepository : KoinComponent {
    private val recipeRemoteSource: RecipeRemoteSource by inject()

    suspend fun postRecipe(recipeRequest: RecipeRequest): Long? = recipeRemoteSource.postRecipe(recipeRequest)
    suspend fun getRecipes(): List<RecipeResponse> = recipeRemoteSource.getRecipes()
    suspend fun getRecipe(id: Long): RecipeResponse = recipeRemoteSource.getRecipe(id)

}
