package com.example.homework1.compose

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.homework1.Importance
import com.example.homework1.R
import com.example.homework1.TodoItem
import com.example.homework1.TodoViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(id: String?, navController: NavHostController,
              viewModel: TodoViewModel,) {

    var todoText by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("Дата и время") }
    var todoImportance by remember { mutableStateOf(Importance.NORMAL) }
    var selectedImportance by remember { mutableStateOf("Нет") }
    var todoItem : TodoItem? by remember { mutableStateOf(null) }
    var deadline : Date? by remember { mutableStateOf(null) }
    val context = LocalContext.current

    if(id != null) {
        todoItem = viewModel.getItemById(id)
        if (todoItem != null) {
            todoText = todoItem!!.text!!
            todoImportance = todoItem!!.importance
            selectedImportance = getStringFromImportance(todoImportance)
            deadline = todoItem!!.deadline
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.close),
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            var newId = "10"
                            var createdAt = Date()
                            var isDone = false
                            var updatedAt : Date? = null
                            if(id != null) {
                                newId = id
                                isDone = todoItem!!.isDone
                                updatedAt = Date()
                                createdAt = todoItem!!.createdAt
                            }

                            val todoItemNew = TodoItem(
                                id = newId,
                                text = todoText,
                                importance = getImportanceFromString(selectedImportance),
                                deadline = deadline,
                                isDone = false,
                                createdAt = Date(),
                                updatedAt = null
                            )
                            viewModel.addOrEditTodoItem(todoItemNew)
                            navController.popBackStack()
                        },
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Text(
                            text = "СОХРАНИТЬ",
                            color = MaterialTheme.colorScheme.tertiary,
                            fontSize = 16.sp
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 72.dp)
                    .padding(top = 72.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Gray.copy(alpha = 0.1f))
                    .padding(16.dp),
                contentAlignment = Alignment.TopStart
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = todoText,
                    onValueChange = { todoText = it },
                    placeholder = { Text("Что нужно сделать") },
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.Blue,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }

            Text(
                text = "Важность",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 20.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            var expanded by remember { mutableStateOf(false) }
            Box(modifier = Modifier.fillMaxWidth()) {
                TextButton(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = selectedImportance,
                            color = Color.Black,

                            )
                    }
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(Color.Gray)
                ) {
                    DropdownMenuItem(
                        text = { Text("Нет", color = Color.Black) },
                        onClick = {
                            selectedImportance = "Нет"
                            expanded = false
                        },
                        modifier = Modifier.background(Color.Blue),
                    )
                    DropdownMenuItem(
                        text = { Text("Высокая", color = Color.Black) },
                        onClick = {
                            selectedImportance = "Высокая"
                            expanded = false
                        },
                        modifier = Modifier
                            .background(Color.Blue),
                    )
                    DropdownMenuItem(
                        text = { Text("Низкая", color = Color.Black) },
                        onClick = {
                            selectedImportance = "Низкая"
                            expanded = false
                        },
                        modifier = Modifier.background(Color.Blue),
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val dateFormatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
            Row() {
                Column(

                    modifier = Modifier
                        .widthIn()
                        .heightIn()
                ) {
                    Text(
                        text = "Сделать до: ",
                        fontSize = 16.sp,
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = date,
                        fontSize = 16.sp,
                        color = Color.Gray.copy(alpha = 1f)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))

                Switch(
                    checked = deadline != null,
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            val calendar = Calendar.getInstance()
                            val datePickerDialog = DatePickerDialog(
                                context,
                                { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                                    calendar.set(year, month, dayOfMonth)
                                    deadline = calendar.time
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            )
                            datePickerDialog.show()
                        } else {
                            deadline = null
                        }
                    }
                )
                deadline?.let {
                    date = dateFormatter.format(it)
                } ?: run {
                    date = "Дата и время"
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(thickness = 1.dp, color = Color.Gray)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .clickable(onClick = {
                        if(id != null) {
                            val todo = viewModel.getItemById(id.toString())
                            if (todo != null) {
                                viewModel.removeTodoItem(todo)
                            }
                        }
                        navController.popBackStack()
                    })
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.delete_red),
                    contentDescription = "Delete icon",
                    tint = Color.Red,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Удалить",
                    color = Color.Red,
                    fontSize = 16.sp
                )
            }
        }
        AddScreenContent(navController, id, viewModel)
    }
}

fun getStringFromImportance(importance: Importance): String {
    return when (importance) {
        Importance.LOW -> "Низкая"
        Importance.NORMAL -> "Нет"
        Importance.HIGH -> "Высокая"
    }
}

fun getImportanceFromString(importance: String): Importance {
    return when (importance) {
        "Низкая" -> Importance.LOW
        "Нет" -> Importance.NORMAL
        "Высокая" -> Importance.HIGH
        else -> Importance.NORMAL
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreenContent(navController: NavHostController, id: String?, viewModel: TodoViewModel) {

}

@Preview(showBackground = true)
@Composable
fun PreviewAddScreen() {
    val navController = rememberNavController()
    val viewModel = TodoViewModel()
    AddScreen(null, navController = navController, viewModel = viewModel)
}



/*
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Добавить задачу") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.close),
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = { /* Handle save action */ },
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Text(
                            text = "Сохранить",
                            color = Color.Blue,
                            fontSize = 16.sp
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .padding(vertical = 24.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Gray.copy(alpha = 0.1f))
                    .padding(16.dp),
                contentAlignment = Alignment.TopStart
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = "", // Здесь должно быть значение из состояния
                    onValueChange = { /* Handle text change */ },
                    placeholder = { Text("Что нужно сделать") },
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.Blue,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }

            Text(
                text = "Важность",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 20.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Spinner на основе DropdownMenu
            // Для примера используем DropdownMenu как Spinner
            var expanded by remember { mutableStateOf(false) }
            Box(modifier = Modifier.fillMaxWidth()) {
                TextButton(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Выбрать важность",
                        color = Color.Black,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(CustomTheme.colors.backElevated)
                ) {
                    DropdownMenuItem(
                        text = { Text("Нет", color = CustomTheme.colors.labelPrimary) },
                        onClick = {  },
                        modifier = Modifier.background(CustomTheme.colors.backElevated),
                    )
                    DropdownMenuItem(
                        text = { Text("Высокая", color = CustomTheme.colors.colorRed) },
                        onClick = {  },
                        modifier = Modifier.background(CustomTheme.colors.backElevated),
                    )
                    DropdownMenuItem(
                        text = { Text("Низкая", color = CustomTheme.colors.colorRed) },
                        onClick = {  },
                        modifier = Modifier.background(CustomTheme.colors.backElevated),
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "До: ",
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "Дата и время",
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Switch в Compose
                Switch(
                    checked = false, // Здесь должно быть значение из состояния
                    onCheckedChange = { /* Handle switch state change */ },
                    modifier = Modifier.wrapContentSize()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Divider(color = Color.Gray, thickness = 1.dp)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .clickable(onClick = { /* Handle delete action */ })
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.delete_red),
                    contentDescription = "Delete icon",
                    tint = Color.Red,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Удалить",
                    color = Color.Red,
                    fontSize = 16.sp
                )
            }
        }
    }
}
*/
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