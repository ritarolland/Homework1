package com.example.homework1.ui.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework1.data.TodoItemsRepository
import com.example.homework1.domain.models.TodoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repository: TodoItemsRepository
) : ViewModel() {

    private val todoItems = MutableStateFlow<List<TodoItem>>(emptyList())

    private val _mainScreenUiModel = MutableStateFlow(MainScreenUiModel())
    val mainScreenUiModel = _mainScreenUiModel.asStateFlow()

    private val _errorFlow = MutableStateFlow<String?>(null)
    val errorFlow = _errorFlow.asStateFlow()

    init {
        loadTodos()
    }

    private fun runSafeInBackground(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                block.invoke()
            } catch (e: Exception) {
                _errorFlow.value = e.message ?: "Something went wrong"
            }
        }
    }

    private fun loadTodos() {
        runSafeInBackground {
            repository.listOfToDo.collect { todoList ->
                todoItems.value = todoList
                _mainScreenUiModel.value = MainScreenUiModel(
                    tasks = if (_mainScreenUiModel.value.showCompletedTasks) {
                        todoItems.value
                    } else {
                        todoItems.value.filter { !it.isDone }
                    },
                    completedTasksCount = todoItems.value.count { it.isDone },
                    showCompletedTasks = _mainScreenUiModel.value.showCompletedTasks,
                )
            }
        }
    }

    fun toggleShowCompletedTasks() {
        _mainScreenUiModel.value = mainScreenUiModel.value.copy(
            tasks = if (!mainScreenUiModel.value.showCompletedTasks) {
                todoItems.value
            } else {
                todoItems.value.filter { !it.isDone }
            },
            showCompletedTasks = !mainScreenUiModel.value.showCompletedTasks
        )
    }

    fun updateChecked(id: String, isDone: Boolean) {
        runSafeInBackground {
            repository.updateChecked(id, isDone)

        }
    }

    fun clearError() {
        _errorFlow.value = null
    }
}
