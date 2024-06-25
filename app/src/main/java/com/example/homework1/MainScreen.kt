package com.example.homework1
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: TodoViewModel,
    navigateToAdd: () -> Unit,
    navigateToDetails: (String) -> Unit
) {
    val tasks by viewModel.todos.observeAsState(emptyList())
    val completedTasksCount by viewModel.completedTasksCount.observeAsState(0)

    Scaffold(
        topBar = {
            Column {
                LargeTopAppBar(
                    title = { Text(text = "Мои дела") },
                    actions = {
                        Text(text = "Выполнено - $completedTasksCount")
                        IconButton(onClick = { viewModel.toggleShowCompletedTasks() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.visibility),
                                contentDescription = "Toggle Visibility",
                                tint = Color.Black
                            )
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToAdd) {
                Icon(painter = painterResource(id = R.drawable.add), contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.padding(16.dp)
        ) {
            items(tasks) { task ->
                if (task is TodoItem) {
                    task.text?.let {
                        Text(
                            text = it,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable { task.id?.let { navigateToDetails(it) } }
                        )
                    }
                }
            }
        }
    }
}
