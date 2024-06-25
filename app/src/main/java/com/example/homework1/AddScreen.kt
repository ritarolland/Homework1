package com.example.homework1

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(navController: NavHostController) {
    // Your AddScreen content
    /*Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Добавить задачу") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(painter = painterResource(id = R.drawable.close), contentDescription = "Back")
                    }
                }
            )
        }
    ) {

    }*/
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(id: String, navController: NavHostController) {
    // Your DetailScreen content
    /*Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Детали задачи") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(painter = painterResource(id = R.drawable.close), contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        // Content goes here
    }*/
}