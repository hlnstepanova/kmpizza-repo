package dev.tutorial.kmpizza.backend.storage.exposed

import dev.tutorial.kmpizza.model.Ingredient
import dev.tutorial.kmpizza.model.Instruction
import dev.tutorial.kmpizza.model.Recipe
import dev.tutorial.kmpizza.model.RecipeResponse
import java.io.File

internal interface LocalSource {
    suspend fun getPizza(): String
    suspend fun addIngredient(recipeId: Long, ingredient: Ingredient): Long
    suspend fun addInstruction(recipeId: Long, instruction: Instruction): Long
    suspend fun addRecipe(recipe: Recipe): Long
    suspend fun getRecipes() : List<RecipeResponse>
    suspend fun getRecipe(recipeId: Long) : RecipeResponse
    suspend fun saveImage(recipeId: Long, image: File)
}