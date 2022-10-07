package dev.tutorial.kmpizza.android

import android.app.Application
import dev.tutorial.kmpizza.di.initKoin
import org.koin.android.ext.koin.androidContext

@Suppress("unused")
class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MainApp)
        }
    }
}
