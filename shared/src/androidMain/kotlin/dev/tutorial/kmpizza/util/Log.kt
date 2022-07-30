package dev.tutorial.kmpizza.util

import android.util.Log

actual val log: (String) -> Unit = {
    Log.d("RecipesLog", it)
}
