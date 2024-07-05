package com.example.homework1

import com.example.homework1.domain.models.Importance
import com.example.homework1.domain.models.TodoItem
import java.util.Date

sealed interface TodoEvent {
    object SaveTodo : TodoEvent
    data class SetText(val text: String) : TodoEvent
    data class SetImportance(val importance: Importance) : TodoEvent
    data class SetDeadline(val deadline: Date?) : TodoEvent
    data class SetDone(val isDone: Boolean) : TodoEvent
    data class SetCreated(val createdAt: Date) : TodoEvent
    data class SetUpdated(val updatedAt: Date) : TodoEvent
    data class DeleteTodo(val todo: TodoItem) : TodoEvent
}