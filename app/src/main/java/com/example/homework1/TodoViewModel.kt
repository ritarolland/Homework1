package com.example.homework1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TodoViewModel : ViewModel() {

    private val repository = TodoItemsRepository()
    private val _todos = MutableLiveData<List<Any>>()
    private val _completedTasksCount = MutableLiveData<Int>()
    val completedTasksCount: LiveData<Int>
        get() = _completedTasksCount

    private var showCompletedTasks = true

    val todos: LiveData<List<Any>> get() = _todos

    init {
        _todos.value = repository.getAllToDo()
        addNewItem()
    }
    private fun addNewItem() {
        val currentList = _todos.value.orEmpty().toMutableList()
        currentList.add("new")
        _todos.value = currentList
    }

    fun getItemById(id : String) : TodoItem? {
        return repository.getToDoById(id)
    }

    fun addOrEditTodoItem(todoItem: TodoItem) {
        repository.addOrEditToDo(todoItem)
        _todos.value = repository.getAllToDo()
        addNewItem()
    }

    fun removeTodoItem(todoItem: TodoItem) {
        repository.deleteToDo(todoItem)
        _todos.value = repository.getAllToDo() // Обновляем LiveData
        updateCompletedTasksCount()
        addNewItem()
    }
    private fun updateCompletedTasksCount() {
        viewModelScope.launch {
            val count = repository.countOfCompleted()
            _completedTasksCount.value = count
        }
    }

    fun checkItem(item: TodoItem, checked: Boolean) {
        repository.checkItem(item, checked)
        _todos.value = repository.getAllToDo()
        addNewItem()
    }

    fun toggleShowCompletedTasks() {
        showCompletedTasks = !showCompletedTasks
        updateTodos()
        addNewItem()
    }

    private fun updateTodos() {
        _todos.value = repository.filterTodos(showCompletedTasks)
    }

    fun countCompletedTasks(): Int {
        return repository.countOfCompleted()
    }
}
