package com.example.homework1.domain.models

import java.util.Date

data class TodoItem(
    val id: String = "",
    val text: String = "",
    val importance: Importance = Importance.NORMAL,
    val deadline: Date? = null,
    var isDone: Boolean = false,
    val createdAt: Date = Date(),
    val updatedAt: Date? = null
)
