package com.example.homework1

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun NavGraph(navController: NavHostController) {
    val viewModel: TodoViewModel = TodoViewModel()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(
                viewModel = viewModel,
                navigateToAdd = { navController.navigate("add") },
                navigateToDetails = { id -> navController.navigate("details/$id") }
            )
        }
        composable("add") { AddScreen(navController = navController) }
        composable("details/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: return@composable
            DetailScreen(id = id, navController = navController)
        }
    }
}