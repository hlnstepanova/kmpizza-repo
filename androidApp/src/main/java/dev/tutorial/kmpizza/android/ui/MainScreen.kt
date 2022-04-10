package dev.tutorial.kmpizza.android.ui

import androidx.compose.runtime.Composable
import dev.tutorial.kmpizza.remote.RecipeRemoteSource

@Composable
public fun MainScreen(recipeRemoteSource: RecipeRemoteSource) {
    RecipesScreen(recipeRemoteSource)
}
