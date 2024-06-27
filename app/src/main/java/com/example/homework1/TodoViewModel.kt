package com.example.homework1

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TodoViewModel : ViewModel() {

    private val repository = TodoItemsRepository()
    private val _todos = MutableLiveData<List<Any>>()
    val todos: LiveData<List<Any>> get() = _todos
    private val _completedTasksCount = MutableLiveData<Int>()
    val completedTasksCount: LiveData<Int> get() = _completedTasksCount

    private var _showCompletedTasks = MutableLiveData<Boolean>(true)


    val showCompletedTasks: LiveData<Boolean>
        get() = _showCompletedTasks




    init {
        _todos.value = repository.getAllToDo()
        _completedTasksCount.value = repository.countOfCompleted()
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
        updateTodos()
        _completedTasksCount.value = repository.countOfCompleted()
        addNewItem()
    }

    fun removeTodoItem(todoItem: TodoItem) {
        repository.deleteToDo(todoItem)
        updateTodos()
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
        updateTodos()
        addNewItem()
    }

    fun toggleShowCompletedTasks() {
        _showCompletedTasks.value = !_showCompletedTasks.value!!
        updateTodos()
        addNewItem()
    }

    private fun updateTodos() {
        _todos.value = _showCompletedTasks.value?.let { repository.filterTodos(it) }
    }

    fun countCompletedTasks(): Int {
        return repository.countOfCompleted()
    }

}
