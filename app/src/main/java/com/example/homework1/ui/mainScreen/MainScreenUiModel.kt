package com.example.homework1.ui.mainScreen

import com.example.homework1.domain.models.TodoItem

data class MainScreenUiModel(
    val tasks: List<TodoItem> = emptyList(),
    val completedTasksCount: Int = 0,
    val showCompletedTasks: Boolean = true,
)