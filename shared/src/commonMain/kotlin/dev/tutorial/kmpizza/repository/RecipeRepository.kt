package dev.tutorial.kmpizza.repository

import dev.tutorial.kmpizza.model.RecipeUiModel
import dev.tutorial.kmpizza.model.toRecipeRequest
import dev.tutorial.kmpizza.model.toRecipeUiModel
import dev.tutorial.kmpizza.remote.RecipeRemoteSource
import dev.tutorial.kmpizza.util.ImageFile
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RecipeRepository : KoinComponent {
    private val recipeRemoteSource: RecipeRemoteSource by inject()

    suspend fun postRecipe(recipe: RecipeUiModel): Long? =
        recipeRemoteSource.postRecipe(recipe.toRecipeRequest())

    suspend fun getRecipes(): List<RecipeUiModel> =
        recipeRemoteSource.getRecipes().map { it.toRecipeUiModel() }

    suspend fun getRecipe(id: Long): RecipeUiModel =
        recipeRemoteSource.getRecipe(id).toRecipeUiModel()

    suspend fun postRecipeImage(recipeId: Long, imageFile: ImageFile) =
        recipeRemoteSource.postImage(recipeId, imageFile)

}

