package dev.tutorial.kmpizza.backend.storage.exposed.instruction

import dev.tutorial.kmpizza.backend.model.Instruction
import dev.tutorial.kmpizza.backend.storage.exposed.recipe.RecipeEntity
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class InstructionEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<InstructionEntity>(InstructionTable)

    var order by InstructionTable.order
    var description by InstructionTable.description
    var recipe by RecipeEntity referencedOn InstructionTable.recipe
}

fun InstructionEntity.toInstruction() = Instruction(
    id.value.toLong(),
    order,
    description
)
