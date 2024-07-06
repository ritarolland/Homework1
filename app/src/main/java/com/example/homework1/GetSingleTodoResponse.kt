package com.example.homework1

data class GetSingleTodoResponse(
    override val status: String,
    val element: ServerTodoItem,
    override val revision: Int
): TodoResponse