package dev.tutorial.kmpizza.backend

import dev.tutorial.kmpizza.backend.model.Recipe
import dev.tutorial.kmpizza.backend.storage.exposed.LocalSource
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

internal fun Routing.api(localSource: LocalSource) {
    pizza(localSource)
    addRecipe(localSource)
    getRecipes(localSource)
    getRecipe(localSource)
}

private fun Routing.pizza(localSource: LocalSource) {
    get("/pizza") {
        localSource.getPizza().let {
            call.respond(it)
        }
    }
}

private fun Routing.addRecipe(localSource: LocalSource) {
    post("/recipes") {
        val recipeId = localSource.addRecipe(call.receive())
        call.respond(recipeId)
    }
}

private fun Routing.getRecipes(localSource: LocalSource) {
    get("/recipes") {
        localSource.getRecipes().let {
            call.respond(it)
        }
    }
}
private fun Routing.getRecipe(localSource: LocalSource) {
    get("/recipes/{recipeId}") {
        val recipeId = call.parameters["recipeId"]!!.toLong()
        localSource.getRecipe(recipeId).let {
            call.respond(it)
        }
    }
}


