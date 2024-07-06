package com.example.homework1

data class GetTodoListResponse (
    override val status: String,
    val list: List<ServerTodoItem>,
    override val revision: Int
) : TodoResponse