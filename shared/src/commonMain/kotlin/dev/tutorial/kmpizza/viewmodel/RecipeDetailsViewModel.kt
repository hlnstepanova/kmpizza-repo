package dev.tutorial.kmpizza.viewmodel

import dev.tutorial.kmpizza.model.*
import dev.tutorial.kmpizza.repository.RecipeRepository
import dev.tutorial.kmpizza.util.CoroutineViewModel
import dev.tutorial.kmpizza.util.log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RecipeDetailsViewModel(private val id: Long?) : CoroutineViewModel(), KoinComponent,
    EditRecipeChangeListener {
    private val recipeRepository: RecipeRepository by inject()

    private val _recipe = MutableStateFlow<RecipeUiModel?>(
        RecipeUiModel(
            title = "",
            ingredients = listOf(),
            instructions = listOf()
        )
    )
    val recipe: StateFlow<RecipeUiModel?> = _recipe

    private val _upload = MutableStateFlow<Boolean>(false)
    val upload: StateFlow<Boolean> = _upload

    init {
        id?.let { getRecipe(it) }
    }

    fun getRecipe(id: Long) {
        coroutineScope.launch {
            _recipe.value = recipeRepository.getRecipe(id).toRecipeUiModel()
        }
    }

    fun saveRecipe() {
        coroutineScope.launch {
            recipe.value?.let {
                if (it.title.isNotEmpty() && it.ingredients.isNotEmpty() && it.instructions.isNotEmpty()) {
                    log(it.toString())
                    val result = recipeRepository.postRecipe(it.toRecipeRequest())
                    log(result.toString())
                    result?.let { _upload.value = true }
                }
            }
        }
    }


    @Suppress("unused")
    fun observeRecipe(onChange: (RecipeUiModel?) -> Unit) {
        recipe.onEach {
            onChange(it)
        }.launchIn(coroutineScope)
    }

    @Suppress("unused")
    fun observeUpload(onChange: (Boolean?) -> Unit) {
        upload.onEach {
            onChange(it)
        }.launchIn(coroutineScope)
    }

    override fun onTitleChanged(title: String) {
        _recipe.value = _recipe.value?.copy(title = title)

    }

    override fun onIngredientsChanged(ingredient: Ingredient) {
        val ingredients = _recipe.value?.ingredients
        _recipe.value =
            _recipe.value?.copy(ingredients = ingredients?.plus(ingredient) ?: listOf(ingredient))

    }

    override fun onInstructionsChanged(instruction: Instruction) {
        val instructions = _recipe.value?.instructions
        _recipe.value = _recipe.value?.copy(
            instructions = instructions?.plus(instruction) ?: listOf(instruction)
        )

    }

    fun resetUpload() {
        _upload.value = false
    }
}


interface EditRecipeChangeListener {
    fun onTitleChanged(title: String)
    fun onIngredientsChanged(ingredient: Ingredient)
    fun onInstructionsChanged(instruction: Instruction)
}
