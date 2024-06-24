package com.example.homework1

import java.util.Calendar
import java.util.Date

class TodoItemsRepository {
    private val listOfToDo = mutableListOf<TodoItem>()

    init {
        listOfToDo.apply {
            add(
                TodoItem(
                    id = "1",
                    text = "Task 1",
                    importance = Importance.NORMAL,
                    deadline = Date(),
                    isDone = false,
                    createdAt = Date()
                )
            )
            add(
                TodoItem(
                    id = "2",
                    text = "Task 1",
                    importance = Importance.NORMAL,
                    deadline = Date(),
                    isDone = false,
                    createdAt = Date()
                )
            )
            add(
                TodoItem(
                    id = "3",
                    text = "Task 1",
                    importance = Importance.NORMAL,
                    deadline = Date(),
                    isDone = false,
                    createdAt = Date()
                )
            )
            add(
                TodoItem(
                    id = "4",
                    text = "Task 1",
                    importance = Importance.HIGH,
                    deadline = Date(),
                    isDone = false,
                    createdAt = Date()
                )
            )
            add(
                TodoItem(
                    id = "5",
                    text = "Task 1",
                    importance = Importance.NORMAL,
                    deadline = Date(),
                    isDone = false,
                    createdAt = Date()
                )
            )
        }
    }

    fun getToDoById(id: String?) = listOfToDo.firstOrNull { it.id == id }

    fun getAllToDo() = listOfToDo.toList()

    fun addOrEditToDo(item: TodoItem) {
        if (item.id != null) {
            listOfToDo.replaceAll { if (it.id == item.id) item else it }
        } else {
            listOfToDo.add(0, item.copy(id = Calendar.getInstance().time.toString()))
        }
    }

    fun deleteToDo(item: TodoItem) {
        listOfToDo.remove(item)
    }
    fun deleteItemByPosition(position: Int) {
        listOfToDo.removeAt(position)
    }

    fun filterTodos(showCompletedTasks: Boolean): List<TodoItem> {
        return if (showCompletedTasks) {
            listOfToDo
        } else {
            listOfToDo.filter { !it.isDone }
        }
    }

    fun checkItem(item: TodoItem, checked: Boolean) {
        val index = listOfToDo.indexOf(item)
        if (index != -1) {
            listOfToDo[index].isDone = checked
        }
    }


    fun countOfCompleted() = listOfToDo.count { it.isDone }
}