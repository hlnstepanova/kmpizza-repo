package dev.tutorial.kmpizza.backend.storage.exposed.image

import dev.tutorial.kmpizza.backend.model.RecipeImage
import dev.tutorial.kmpizza.backend.storage.exposed.recipe.RecipeEntity
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class RecipeImageEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RecipeImageEntity>(RecipeImageTable)

    var image by RecipeImageTable.image
    var recipe by RecipeEntity referencedOn RecipeImageTable.recipe
}

fun RecipeImageEntity.toRecipeImage() = RecipeImage(id.value.toLong(), image)
