package dev.tutorial.kmpizza.android
 
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import dev.tutorial.kmpizza.android.ui.MainScreen
import dev.tutorial.kmpizza.api.KtorApiImpl
import dev.tutorial.kmpizza.api.RecipesApi
import dev.tutorial.kmpizza.remote.RecipeRemoteSource

class MainActivity : ComponentActivity() {
    val recipeRemoteSource = RecipeRemoteSource(RecipesApi(KtorApiImpl()))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MainScreen(recipeRemoteSource = recipeRemoteSource)
            }
        }
    }
}

