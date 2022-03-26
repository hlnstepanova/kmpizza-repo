package dev.tutorial.kmpizza.backend.storage.exposed.recipe

import dev.tutorial.kmpizza.backend.model.Recipe
import dev.tutorial.kmpizza.backend.model.RecipeResponse
import dev.tutorial.kmpizza.backend.storage.exposed.image.RecipeImageEntity
import dev.tutorial.kmpizza.backend.storage.exposed.image.RecipeImageTable
import dev.tutorial.kmpizza.backend.storage.exposed.image.toRecipeImage
import dev.tutorial.kmpizza.backend.storage.exposed.ingredient.IngredientEntity
import dev.tutorial.kmpizza.backend.storage.exposed.ingredient.IngredientTable
import dev.tutorial.kmpizza.backend.storage.exposed.ingredient.toIngredient
import dev.tutorial.kmpizza.backend.storage.exposed.instruction.InstructionEntity
import dev.tutorial.kmpizza.backend.storage.exposed.instruction.InstructionTable
import dev.tutorial.kmpizza.backend.storage.exposed.instruction.toInstruction
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class RecipeEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RecipeEntity>(RecipeTable)

    var title by RecipeTable.title
    val ingredients by IngredientEntity referrersOn IngredientTable.recipe
    val instructions by InstructionEntity referrersOn InstructionTable.recipe
    val recipeImages by RecipeImageEntity referrersOn RecipeImageTable.recipe

}

fun RecipeEntity.toRecipeResponse() = RecipeResponse(
    id.value.toLong(),
    title,
    ingredients.map { it.toIngredient() },
    instructions.map { it.toInstruction() },
    recipeImages.map { it.toRecipeImage() })