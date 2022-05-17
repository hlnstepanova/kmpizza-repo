package dev.tutorial.kmpizza.backend

import dev.tutorial.kmpizza.backend.storage.exposed.LocalSource
import dev.tutorial.kmpizza.backend.utils.toFile
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

internal fun Routing.api(localSource: LocalSource) {
    pizza(localSource)
    addRecipe(localSource)
    getRecipes(localSource)
    getRecipe(localSource)
    addRecipeImage(localSource)
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

private fun Routing.addRecipeImage(localSource: LocalSource) {
    post("/recipes/{recipeId}/recipeImage") {
        val recipeId = call.parameters["recipeId"]!!.toLong()

        var image: File? = null

        call.receiveMultipart().forEachPart {
            when (it) {
                is PartData.FileItem -> image = it.toFile()
                else -> Unit
            }
            it.dispose()
        }
        localSource.saveImage(
            recipeId,
            image ?: throw BadRequestException("image part is missing")
        )
        image?.delete()
        call.respond(HttpStatusCode.OK)
    }
}


