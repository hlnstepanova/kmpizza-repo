package dev.tutorial.kmpizza.di

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import dev.tutorial.kmpizza.db.KmpizzaDatabase
import org.koin.dsl.module

actual val platformModule = module {
    single<SqlDriver> { NativeSqliteDriver(KmpizzaDatabase.Schema, "kmpizza.db") }
}
