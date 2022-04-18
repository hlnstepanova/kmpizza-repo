package dev.tutorial.kmpizza.android

import android.app.Application
import dev.tutorial.kmpizza.di.initKoin

@Suppress("unused")
class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
        }
    }
}
