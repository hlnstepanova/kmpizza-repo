package dev.tutorial.kmpizza.backend.model

data class Recipe(
    val title: String,
    val ingredients: List<Ingredient>,
    val instructions: List<Instruction>

)

data class Ingredient(
    val name: String,
    val amount: Double,
    val metric: String

)

data class Instruction(
    val order: Int,
    val description: String,
)
