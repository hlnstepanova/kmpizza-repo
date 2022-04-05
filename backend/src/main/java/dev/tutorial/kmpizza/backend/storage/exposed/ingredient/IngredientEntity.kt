package dev.tutorial.kmpizza.backend.storage.exposed.ingredient

import dev.tutorial.kmpizza.backend.storage.exposed.recipe.RecipeEntity
import dev.tutorial.kmpizza.model.Ingredient
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class IngredientEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<IngredientEntity>(IngredientTable)

    var name by IngredientTable.name
    var amount by IngredientTable.amount
    var metric by IngredientTable.metric
    var recipe by RecipeEntity referencedOn IngredientTable.recipe
}

fun IngredientEntity.toIngredient() = Ingredient(
    id.value.toLong(),
    name,
    amount.toDouble(),
    metric
)
