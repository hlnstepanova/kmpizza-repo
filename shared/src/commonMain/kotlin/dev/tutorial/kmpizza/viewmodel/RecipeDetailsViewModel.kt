package dev.tutorial.kmpizza.viewmodel

import dev.tutorial.kmpizza.model.Ingredient
import dev.tutorial.kmpizza.model.Instruction
import dev.tutorial.kmpizza.model.RecipeUiModel
import dev.tutorial.kmpizza.repository.RecipeRepository
import dev.tutorial.kmpizza.util.CoroutineViewModel
import dev.tutorial.kmpizza.util.ImageFile
import dev.tutorial.kmpizza.util.log
import kotlinx.coroutines.async
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
            instructions = listOf(),
            images = listOf()
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
            _recipe.value = recipeRepository.getRecipe(id)
        }
    }

    fun saveRecipe() {
        coroutineScope.launch {
            recipe.value?.let { recipe ->
                if (recipe.title.isNotEmpty() && recipe.ingredients.isNotEmpty() && recipe.instructions.isNotEmpty()){
                    log("Post recipe: $recipe")
                    val result = recipeRepository.postRecipe(recipe)
                    val imageUploadRequest = recipe.localImage.let { image ->
                        async {
                            result?.let { it ->
                                if (image != null) {
                                    recipeRepository.postRecipeImage(it, image)
                                }
                            }
                        }
                    }
                    log("Shared result: $result")
                    imageUploadRequest.await()
                    result?.let {
                        _upload.value = true
                        log("Shared upload value: ${_upload.value}")
                    }
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

    override fun onImageChanged(image: ImageFile) {
        log("Local image: $image")
        _recipe.value = _recipe.value?.copy(localImage = image)
    }

    fun resetUpload() {
        _upload.value = false
    }
}


interface EditRecipeChangeListener {
    fun onTitleChanged(title: String)
    fun onIngredientsChanged(ingredient: Ingredient)
    fun onInstructionsChanged(instruction: Instruction)
    fun onImageChanged(image: ImageFile)
}
