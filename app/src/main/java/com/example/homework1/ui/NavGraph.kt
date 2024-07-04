package com.example.homework1.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.homework1.ui.addScreen.AddScreen
import com.example.homework1.ui.mainScreen.MainScreenContent
import com.example.homework1.ui.mainScreen.TodoViewModel
import com.example.homework1.ui.addScreen.AddScreenViewModel


@Composable
fun NavGraph(navController: NavHostController) {
    val viewModel: TodoViewModel = viewModel()

    val addScreenViewModel: AddScreenViewModel = viewModel()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreenContent(
                viewModel = viewModel,
                navigateToAdd = { id ->
                    if (id != null) {
                        navController.navigate("add/$id")
                    } else {
                        navController.navigate("add") {
                            launchSingleTop = true
                        }
                    }
                },
                addScreenViewModel = addScreenViewModel
            )
        }
        composable("add/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            AddScreen(navController = navController, viewModel = addScreenViewModel)////////////////////
        }
        composable("add") {
            AddScreen(navController = navController, viewModel = addScreenViewModel)//////////////////
        }
    }
}