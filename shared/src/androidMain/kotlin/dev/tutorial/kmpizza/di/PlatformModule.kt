package dev.tutorial.kmpizza.di

import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dev.tutorial.kmpizza.db.KmpizzaDatabase
import org.koin.dsl.module

actual val platformModule = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            KmpizzaDatabase.Schema,
            get(),
            "kmpizza.db"
        )
    }
}
