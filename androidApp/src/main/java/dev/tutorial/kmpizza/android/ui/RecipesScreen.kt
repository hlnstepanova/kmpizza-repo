package dev.tutorial.kmpizza.android.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import dev.tutorial.kmpizza.api.KtorApi
import dev.tutorial.kmpizza.api.KtorApiImpl
import dev.tutorial.kmpizza.api.RecipesApi
import dev.tutorial.kmpizza.model.Recipe
import dev.tutorial.kmpizza.model.RecipeResponse
import dev.tutorial.kmpizza.remote.RecipeRemoteSource
import dev.tutorial.kmpizza.viewmodel.RecipeViewModel

@Composable
public fun RecipesScreen(recipeRemoteSource: RecipeRemoteSource) {
    val viewModel = remember {
        RecipeViewModel(recipeRemoteSource)
    }
    val recipes by viewModel.recipes.collectAsState()

    Recipes (items = recipes)
}

@Composable
fun Recipes(
    items: List<RecipeResponse>
) {
    LazyColumn {
        itemsIndexed(items = items,
            itemContent = { _, item ->
                Text(text = item.title)
            })

    }
}
