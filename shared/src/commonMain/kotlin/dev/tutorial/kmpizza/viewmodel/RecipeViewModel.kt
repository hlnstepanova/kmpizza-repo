package dev.tutorial.kmpizza.viewmodel

import dev.tutorial.kmpizza.model.RecipeResponse
import dev.tutorial.kmpizza.remote.RecipeRemoteSource
import dev.tutorial.kmpizza.util.CoroutineViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class RecipeViewModel (private val recipeRemoteSource: RecipeRemoteSource) : CoroutineViewModel() {
    private val _recipes = MutableStateFlow<List<RecipeResponse>>(emptyList())
    val recipes: StateFlow<List<RecipeResponse>> = _recipes

    init {
        getRecipes()
    }

    fun getRecipes() {
        coroutineScope.launch {
            _recipes.value = recipeRemoteSource.getRecipes()
        }
    }
 
    fun observeRecipes(onChange: (List<RecipeResponse>) -> Unit) {
        recipes.onEach {
            onChange(it)
        }.launchIn(coroutineScope)
    }

}
