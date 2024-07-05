package com.example.homework1.data

import com.example.homework1.domain.models.Importance
import com.example.homework1.domain.models.TodoItem
import com.example.homework1.domain.models.TodoItemDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.util.Date
import java.util.UUID
import javax.inject.Inject


interface TodoItemsRepository {
    fun getAllTodoItems(): Flow<List<TodoItem>>
    suspend fun updateChecked(id: String, isDone: Boolean)
    suspend fun getToDoById(id: String): TodoItem
    suspend fun addOrEditToDo(item: TodoItem)
    suspend fun deleteToDo(id: String)
}

class TodoItemsRepositoryImpl @Inject constructor(
    private val todoItemDao: TodoItemDao
) : TodoItemsRepository {

    val listOfToDo: Flow<List<TodoItem>> = todoItemDao.getAll()


    /*fun updateChecked(id: String, isDone: Boolean) {
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
    }*/

    override fun getAllTodoItems(): Flow<List<TodoItem>> {
        return todoItemDao.getAll()
    }

    override suspend fun updateChecked(id: String, isDone: Boolean) {
        todoItemDao.updateChecked(id, isDone)
    }

    override suspend fun getToDoById(id: String): TodoItem {
        return todoItemDao.getToDoById(id)
    }

    override suspend fun addOrEditToDo(item: TodoItem) {
        todoItemDao.upsert(item)
    }

    override suspend fun deleteToDo(id: String) {
        todoItemDao.deleteToDoById(id)
    }


}