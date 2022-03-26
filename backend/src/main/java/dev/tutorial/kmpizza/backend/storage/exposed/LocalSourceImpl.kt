package dev.tutorial.kmpizza.backend.storage.exposed

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dev.tutorial.kmpizza.backend.model.Ingredient
import dev.tutorial.kmpizza.backend.model.Instruction
import dev.tutorial.kmpizza.backend.model.Recipe
import dev.tutorial.kmpizza.backend.model.RecipeResponse
import dev.tutorial.kmpizza.backend.storage.aws.FileStorage
import dev.tutorial.kmpizza.backend.storage.exposed.image.RecipeImageEntity
import dev.tutorial.kmpizza.backend.storage.exposed.image.RecipeImageTable
import dev.tutorial.kmpizza.backend.storage.exposed.ingredient.IngredientEntity
import dev.tutorial.kmpizza.backend.storage.exposed.ingredient.IngredientTable
import dev.tutorial.kmpizza.backend.storage.exposed.instruction.InstructionEntity
import dev.tutorial.kmpizza.backend.storage.exposed.instruction.InstructionTable
import dev.tutorial.kmpizza.backend.storage.exposed.recipe.RecipeEntity
import dev.tutorial.kmpizza.backend.storage.exposed.recipe.RecipeTable
import dev.tutorial.kmpizza.backend.storage.exposed.recipe.toRecipeResponse
import io.ktor.application.*
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import kotlin.coroutines.CoroutineContext

@OptIn(ObsoleteCoroutinesApi::class)
internal class LocalSourceImpl(
    private val fileStorage: FileStorage,
    application: io.ktor.application.Application
) : LocalSource {
    private val dispatcher: CoroutineContext

    init {
        val config = application.environment.config.config("database")
        val url = System.getenv("JDBC_DATABASE_URL")
        val driver = config.property("driver").getString()
        val poolSize = config.property("poolSize").getString().toInt()
        application.log.info("Connecting to db at $url")

        dispatcher = newFixedThreadPoolContext(poolSize, "database-pool")
        val hikariConfig = HikariConfig().apply {
            jdbcUrl = url
            maximumPoolSize = poolSize
            driverClassName = driver
            validate()
        }

        Database.connect(HikariDataSource(hikariConfig))

        transaction {
            SchemaUtils.createMissingTablesAndColumns(
                RecipeTable,
                IngredientTable,
                InstructionTable,
                RecipeImageTable
            )
        }
    }

    override suspend fun getPizza(): String = withContext(dispatcher) {
        "Pizza!"
    }

    override suspend fun addIngredient(recipeId: Long, ingredient: Ingredient) = withContext(dispatcher) {
        transaction {
            val recipe = RecipeEntity[recipeId.toInt()]
            IngredientEntity.new {
                name = ingredient.name
                amount = ingredient.amount.toBigDecimal()
                metric = ingredient.metric
                this.recipe = recipe
            }.id.value.toLong()
        }
    }

    override suspend fun addInstruction(recipeId: Long, instruction: Instruction) = withContext(dispatcher) {
        transaction {
            val recipe = RecipeEntity[recipeId.toInt()]
            InstructionEntity.new {
                order = instruction.order
                description = instruction.description
                this.recipe = recipe
            }.id.value.toLong()
        }
    }

    override suspend fun addRecipe(recipe: Recipe) = withContext(dispatcher) {
        withContext(dispatcher) {
            val recipeId = transaction {
                RecipeEntity.new {
                    title = recipe.title
                }.id.value.toLong()
            }

            recipe.ingredients.forEach{
                addIngredient(recipeId, it)
            }

            recipe.instructions.forEach{
                addInstruction(recipeId, it)
            }

            recipeId
        }

    }


    override suspend fun getRecipes() : List<RecipeResponse> = withContext(dispatcher) {
        transaction {
            RecipeEntity.all().map { it.toRecipeResponse() }
        }
    }

    override suspend fun getRecipe(recipeId: Long): RecipeResponse = withContext(dispatcher) {
        transaction {
            RecipeEntity[recipeId.toInt()].toRecipeResponse()
        }
    }

    override suspend fun saveImage(recipeId: Long, image: File) {
        withContext(dispatcher) {
            val imageUrl = fileStorage.save(image)
            transaction {
                val recipe = RecipeEntity[recipeId.toInt()]
                RecipeImageEntity.new {
                    this.image = imageUrl
                    this.recipe = recipe
                }
            }
        }
    }


}

