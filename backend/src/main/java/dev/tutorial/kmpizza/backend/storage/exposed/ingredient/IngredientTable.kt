package dev.tutorial.kmpizza.backend.storage.exposed.ingredient

import dev.tutorial.kmpizza.backend.storage.exposed.recipe.RecipeTable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

internal object IngredientTable : IntIdTable("ingredients") {
    val name = varchar("name", 100)
    val amount = decimal("amount", 2, 1)
    val metric = varchar("metric", 100)
    val recipe = reference(
        "recipe_id",
        RecipeTable,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )
}
