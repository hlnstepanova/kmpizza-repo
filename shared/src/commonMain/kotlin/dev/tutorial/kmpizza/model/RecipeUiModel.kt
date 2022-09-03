package dev.tutorial.kmpizza.model

data class RecipeUiModel(
    val id: Long = 0,
    val title: String,
    val ingredients: List<Ingredient> = listOf(),
    val instructions: List<Instruction> = listOf(),
    val images: List<RecipeImage> = listOf()
)

fun RecipeResponse.toRecipeUiModel() = RecipeUiModel(
    id = id,
    title = title,
    ingredients = ingredients,
    instructions = instructions,
    images = images
)

fun RecipeUiModel.toRecipeRequest() = RecipeRequest(
    id = id,
    title = title,
    ingredients = ingredients,
    instructions = instructions
)