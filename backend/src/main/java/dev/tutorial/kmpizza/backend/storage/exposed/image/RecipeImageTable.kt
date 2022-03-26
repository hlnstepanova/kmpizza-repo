package dev.tutorial.kmpizza.backend.storage.exposed.image

import dev.tutorial.kmpizza.backend.storage.exposed.recipe.RecipeTable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object RecipeImageTable : IntIdTable("recipeImages") {
    val image = varchar("image", 1024)
    val recipe = reference(
        "recipe_id",
        RecipeTable,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )
}
