package dev.tutorial.kmpizza.android.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.tutorial.kmpizza.android.ui.utils.Navigation

@Composable
public fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    NavHost(
        navController = navController,
        startDestination = Navigation.Recipes.route,
        builder = {
            composable(Navigation.Recipes.route) {
                RecipesScreen(
                    onRecipeClicked =
                    { navController.navigate("${Navigation.RecipeDetail.route}/${it.id}") }
                )
            }
            composable(
                "${Navigation.RecipeDetail.route}/{id}",
                arguments = listOf(navArgument("id") { type = NavType.LongType })
            ) {
                RecipeDetailScreen(
                    recipeId = it.arguments!!.getLong("id"),
                    upPress = { navController.popBackStack() })
            }
        })
}

