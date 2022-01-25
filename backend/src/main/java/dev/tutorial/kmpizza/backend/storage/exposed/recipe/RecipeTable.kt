package dev.tutorial.kmpizza.backend.storage.exposed.recipe

import org.jetbrains.exposed.dao.id.IntIdTable

internal object RecipeTable : IntIdTable("recipes") {
    val title = varchar("title", 100)
}
