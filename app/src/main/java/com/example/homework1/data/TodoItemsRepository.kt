package com.example.homework1.data

import com.example.homework1.domain.models.Importance
import com.example.homework1.domain.models.TodoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.util.Date
import java.util.UUID

class TodoItemsRepository {
    val listOfToDo = MutableStateFlow(
        listOf(
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "Task 1",
                importance = Importance.NORMAL,
                deadline = Date(),
                isDone = false,
                createdAt = Date()
            ),
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "Task 2",
                importance = Importance.NORMAL,
                deadline = Date(),
                isDone = false,
                createdAt = Date()
            )

        )
    )

    fun updateChecked(id: String, isDone: Boolean) {
        listOfToDo.update { list ->
            list.map {
                if (it.id == id) it.copy(isDone = isDone)
                else it
            }
        }
    }

    fun getToDoById(id: String): TodoItem {
        val todoItem = listOfToDo.value.first { it.id == id }
        return todoItem
    }

    fun addOrEditToDo(item: TodoItem) {
        if (listOfToDo.value.none { it.id == item.id }) {
            listOfToDo.value += item
        } else {
            listOfToDo.update { list ->
                list.map {
                    if (it.id == item.id) {
                        item
                    } else it
                }
            }
        }
    }

    fun deleteToDo(id: String) {
        listOfToDo.value = listOfToDo.value.filterNot { it.id == id }
    }
}