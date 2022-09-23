package dev.tutorial.kmpizza.remote

import dev.tutorial.kmpizza.api.RecipesApi
import dev.tutorial.kmpizza.model.RecipeRequest
import dev.tutorial.kmpizza.util.ImageFile

class RecipeRemoteSource(
    private val recipesApi: RecipesApi
) {
    suspend fun getRecipes() = recipesApi.getRecipes()
    suspend fun postRecipe(recipeRequest: RecipeRequest) = recipesApi.postRecipe(recipeRequest)
    suspend fun getRecipe(id: Long) = recipesApi.getRecipe(id)
    suspend fun postImage(recipeId: Long, imageFile: ImageFile) = recipesApi.postImage(recipeId, imageFile)
}

