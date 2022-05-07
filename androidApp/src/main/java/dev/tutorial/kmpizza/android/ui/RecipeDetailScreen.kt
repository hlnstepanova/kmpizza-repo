package dev.tutorial.kmpizza.android.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import coil.compose.rememberAsyncImagePainter
import dev.tutorial.kmpizza.android.R
import dev.tutorial.kmpizza.model.Ingredient
import dev.tutorial.kmpizza.model.Instruction
import dev.tutorial.kmpizza.viewmodel.RecipeViewModel

@Composable
public fun RecipeDetailScreen(recipeId: Long, upPress: () -> Unit) {
    val viewModel = remember { RecipeViewModel() }
    val recipes by viewModel.recipes.collectAsState()
    val recipe = recipes.find { it.id == recipeId }
    val placeholder = "https://m.media-amazon.com/images/I/413qxEF0QPL._AC_.jpg"

    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderImage(image = placeholder)
        recipe?.let { Title(it.title) }
        SectionHeader(title = "Ingredients")
        recipe?.let { Ingredients(it.ingredients) }
        SectionHeader(title = "Instructions")
        recipe?.let { Instructions(it.instructions) }
    }
}

@Composable
private fun HeaderImage(image: String) {
    Column(modifier = Modifier.padding(8.dp)) {
        AsyncImage(
            model = image,
            contentDescription = null
        )
    }
}

@Composable
private fun Ingredients(items: List<Ingredient>) {
    Column {
        items.forEach {
            IngredientItem(it.name, it.amount, it.metric)
        }
    }
}

@Composable
private fun Instructions(items: List<Instruction>) {
    Column {
        items.forEach {
            InstructionItem(it.order, it.description)
        }
    }
}

@Composable
private fun Title(title: String) {
    Text(
        text = title
    )
}


@Composable
private fun IngredientItem(name: String, amount: Double, metric: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = name
        )
        Row {
            Text(text = amount.toString())
            Text(modifier = Modifier.padding(horizontal = 4.dp), text = metric)
        }
    }
}


@Composable
private fun InstructionItem(order: Int, description: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(text = "$order.")
        Text(modifier = Modifier.padding(horizontal = 16.dp), text = description)
    }
}

@Composable
private fun SectionHeader(title: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(R.drawable.ornament),
            contentDescription = null,
        )
        Text(text = title)
    }
}

@Preview
@Composable
fun IngredientItemPreview(
) {
    val name = "Flour"
    val amount = 2.0
    val metric = "cup"
    IngredientItem(name, amount, metric)
}

@Preview
@Composable
fun InstructionItemPreview(
) {
    val order = 1
    val text = "Sieve the dough into a big bowl"
    InstructionItem(order, text)
}
