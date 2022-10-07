package dev.tutorial.kmpizza.repository

import dev.tutorial.kmpizza.local.RecipeLocalSource
import dev.tutorial.kmpizza.model.RecipeUiModel
import dev.tutorial.kmpizza.model.toRecipeRequest
import dev.tutorial.kmpizza.model.toRecipeUiModel
import dev.tutorial.kmpizza.remote.RecipeRemoteSource
import dev.tutorial.kmpizza.util.ImageFile
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RecipeRepository : KoinComponent {
    private val recipeRemoteSource: RecipeRemoteSource by inject()
    private val recipeLocalSource: RecipeLocalSource by inject()

    suspend fun postRecipe(recipe: RecipeUiModel): Long? {
        return try {
            val id = recipeRemoteSource.postRecipe(recipe.toRecipeRequest())
            id?.let {
                val entry = recipe.copy(id = it)
                recipeLocalSource.insertOrReplaceRecipe(entry)
            }
            id
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getRecipes(): List<RecipeUiModel> {
        return try {
            val recipes = recipeRemoteSource.getRecipes().map {
                it.toRecipeUiModel()
            }
            recipes.forEach {
                recipeLocalSource.insertOrReplaceRecipe(it)
            }
            recipes
        } catch (e: Exception) {
            recipeLocalSource.getAllRecipes()
        }
    }

    suspend fun getRecipe(id: Long): RecipeUiModel? {
        return try {
            recipeRemoteSource.getRecipe(id).toRecipeUiModel()
        } catch (e: Exception) {
            recipeLocalSource.getRecipeById(id)
        }
    }

    suspend fun postRecipeImage(recipeId: Long, imageFile: ImageFile) =
        recipeRemoteSource.postImage(recipeId, imageFile)

}

