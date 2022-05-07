package dev.tutorial.kmpizza.android.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.tutorial.kmpizza.model.RecipeResponse
import dev.tutorial.kmpizza.viewmodel.RecipeViewModel

@Composable
public fun RecipesScreen(onRecipeClicked: (RecipeResponse) -> Unit) {
    val viewModel = remember {
        RecipeViewModel()
    }
    val recipes by viewModel.recipes.collectAsState()

    Recipes(items = recipes, onRecipeClicked = onRecipeClicked)
}

@Composable
fun Recipes(
    items: List<RecipeResponse>,
    onRecipeClicked: (RecipeResponse) -> Unit

) {
    LazyColumn {
        itemsIndexed(
            items = items,
            itemContent = { _, item ->
                RecipeListItem(recipe = item, onRecipeClicked = onRecipeClicked)
            }
        )
    }
}

@Composable
fun RecipeListItem(
    recipe: RecipeResponse,
    onRecipeClicked: (RecipeResponse) -> Unit
) {
    val placeholder = "https://m.media-amazon.com/images/I/413qxEF0QPL._AC_.jpg"
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(128.dp)
            .clickable { onRecipeClicked(recipe) }

    ) {
        AsyncImage(
            modifier = Modifier.padding(8.dp),
            model = placeholder,
            contentDescription = null
        )
        Text(
            text = recipe.title
        )
    }
}

@Preview
@Composable
fun RecipeListItemPreview(
) {
    val title = "Best Dish in the World"
    val recipe = RecipeResponse(id = 0, title = title, listOf(), listOf(), listOf())
    RecipeListItem(recipe) {}
}


