package dev.tutorial.kmpizza.backend.storage.exposed

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dev.tutorial.kmpizza.backend.storage.exposed.ingredient.IngredientTable
import dev.tutorial.kmpizza.backend.storage.exposed.instruction.InstructionTable
import dev.tutorial.kmpizza.backend.storage.exposed.recipe.RecipeTable
import io.ktor.application.*
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.coroutines.CoroutineContext

@OptIn(ObsoleteCoroutinesApi::class)
internal class LocalSourceImpl(
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
                InstructionTable
            )
        }
    }

    override suspend fun getPizza(): String = withContext(dispatcher) {
        "Pizza!"
    }
}

