package com.example.homework1.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.homework1.R
import com.example.homework1.TodoItem
import com.example.homework1.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContent(
    viewModel: TodoViewModel,
    navigateToAdd: (String?) -> Unit
) {
    val tasks by viewModel.todos.observeAsState(emptyList())
    val completedTasksCount by viewModel.completedTasksCount.observeAsState(0)
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val showCompletedTasks by viewModel.showCompletedTasks.observeAsState(true)

    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
                LargeTopAppBar(
                    modifier = Modifier.background(MaterialTheme.colorScheme.primary),
                    title = { Text(text = "Мои дела") },
                    scrollBehavior = scrollBehavior,
                    actions = {
                        Text(text = "Выполнено - $completedTasksCount")
                        IconButton(onClick = {
                            viewModel.toggleShowCompletedTasks()
                        }) {
                            Icon(
                                painter = painterResource(
                                    id = if (showCompletedTasks) R.drawable.visibility else R.drawable.visibility_off
                                ),
                                contentDescription = "Toggle Visibility",
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    }


                )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToAdd(null) },
                containerColor = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier
                    .clip(CircleShape)
            ) {
                Icon(painter = painterResource(id = R.drawable.add), contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .padding(8.dp),
            ) {
                LazyColumn(
                    modifier = Modifier
                        .border(
                        width = 8.dp,
                        color = MaterialTheme.colorScheme.surfaceTint,
                        shape = RoundedCornerShape(8.dp)
                    ),
                    contentPadding = paddingValues
                ) {
                    items(tasks) { task ->
                        when (task) {
                            is TodoItem -> {
                                var isChecked by remember { mutableStateOf(task.isDone) }
                                Todo(
                                    item = task,
                                    onComplete = {
                                        isChecked = !isChecked
                                        task.isDone = it

                                        viewModel.addOrEditTodoItem(task)

                                    },
                                    onCardClick = {
                                        navigateToAdd(task.id)
                                    }
                                    )

                            }
                            else -> {
                                Text(
                                    text = task.toString(),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(52.dp)
                                        .padding(horizontal = 52.dp, vertical = 8.dp)

                                )
                            }
                        }
                    }
                }
        }

    }
}



@Preview
@Composable
fun PreviewMainScreen() {
    val viewModel = TodoViewModel() // Замените на ваш ViewModel
    // В качестве аргументов для navigateToAdd и navigateToDetails
    // можно передать заглушки или значения по умолчанию для предварительного просмотра
    MainScreenContent(viewModel) {}
}
