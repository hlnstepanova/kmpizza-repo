package dev.tutorial.kmpizza.backend.model

import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    val id: Long = 0,
    val title: String,
    val ingredients: List<Ingredient>,
    val instructions: List<Instruction>
)

@Serializable
data class Ingredient(
    val id: Long = 0,
    val name: String,
    val amount: Double,
    val metric: String
)
@Serializable
data class Instruction(
    val id: Long = 0,
    val order: Int,
    val description: String,
)
