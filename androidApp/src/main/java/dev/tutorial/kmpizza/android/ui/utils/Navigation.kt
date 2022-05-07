package dev.tutorial.kmpizza.android.ui.utils

sealed class Navigation(val route: String) {

    object Recipes : Navigation("recipes")
    object RecipeDetail : Navigation("recipeDetail")
}
