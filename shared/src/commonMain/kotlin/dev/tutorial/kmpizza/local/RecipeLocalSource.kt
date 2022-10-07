package dev.tutorial.kmpizza.local

import com.squareup.sqldelight.ColumnAdapter
import dev.tutorial.kmpizza.db.KmpizzaDatabase
import dev.tutorial.kmpizza.db.Recipe
import dev.tutorial.kmpizza.model.Ingredient
import dev.tutorial.kmpizza.model.Instruction
import dev.tutorial.kmpizza.model.RecipeImage
import dev.tutorial.kmpizza.model.RecipeUiModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val listOfInstructionsAdapter = object : ColumnAdapter<List<Instruction>, String> {
    override fun decode(databaseValue: String) = if (databaseValue.isEmpty()) emptyList() else databaseValue.split("|").map { Json.decodeFromString<Instruction>(it) }
    override fun encode(value: List<Instruction>) = if (value.isEmpty()) "" else value.joinToString(separator = "|") { Json.encodeToString(it) }
}

val listOfIngredientsAdapter = object : ColumnAdapter<List<Ingredient>, String> {
    override fun decode(databaseValue: String) = if (databaseValue.isEmpty()) emptyList() else databaseValue.split("|").map { Json.decodeFromString<Ingredient>(it) }
    override fun encode(value: List<Ingredient>) = if (value.isEmpty()) "" else value.joinToString(separator = "|") { Json.encodeToString(it) }
}

val listOfRecipeImagesAdapter = object : ColumnAdapter<List<RecipeImage>, String> {
    override fun decode(databaseValue: String) = if (databaseValue.isEmpty()) emptyList() else databaseValue.split("|").map { Json.decodeFromString<RecipeImage>(it) }
    override fun encode(value: List<RecipeImage>) = if (value.isEmpty()) "" else value.joinToString(separator = "|") { Json.encodeToString(it) }
}

class RecipeLocalSource (private val dbRef: KmpizzaDatabase) {
    fun Recipe.mapToRecipeUiModel(): RecipeUiModel {
        return RecipeUiModel(
            id, title, ingredients, instructions, images
        )
    }

    fun getAllRecipes() : List<RecipeUiModel> =
        dbRef.kmpizzaDatabaseQueries
            .getAllRecipes()
            .executeAsList()
            .map { it.mapToRecipeUiModel() }


    fun getRecipeById(id: Long) : RecipeUiModel? =
        dbRef.kmpizzaDatabaseQueries
            .getRecipeById(id)
            .executeAsOneOrNull()?.mapToRecipeUiModel()


    fun insertOrReplaceRecipe(recipe: RecipeUiModel) {
        dbRef.kmpizzaDatabaseQueries
            .insertOrReplaceRecipe(recipe.id, recipe.title, recipe.ingredients, recipe.instructions, recipe.images)
    }

}


