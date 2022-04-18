package dev.tutorial.kmpizza.viewmodel

import dev.tutorial.kmpizza.model.RecipeResponse
import dev.tutorial.kmpizza.remote.RecipeRemoteSource
import dev.tutorial.kmpizza.repository.RecipeRepository
import dev.tutorial.kmpizza.util.CoroutineViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RecipeViewModel : CoroutineViewModel(), KoinComponent {
    private val recipeRepository: RecipeRepository by inject()

    private val _recipes = MutableStateFlow<List<RecipeResponse>>(emptyList())
    val recipes: StateFlow<List<RecipeResponse>> = _recipes

    init {
        getRecipes()
    }

    fun getRecipes() {
        coroutineScope.launch {
            _recipes.value = recipeRepository.getRecipes()
        }
    }

    fun observeRecipes(onChange: (List<RecipeResponse>) -> Unit) {
        recipes.onEach {
            onChange(it)
        }.launchIn(coroutineScope)
    }

}
