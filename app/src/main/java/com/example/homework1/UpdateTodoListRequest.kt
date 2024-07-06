package com.example.homework1

class UpdateTodoListRequest(
    val status: String,
    val list: List<ServerTodoItem>
)