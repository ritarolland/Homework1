package com.example.homework1

import com.example.homework1.domain.models.TodoItem
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("tasks")
    suspend fun getTasks(): List<TodoItem>

    @POST("tasks")
    suspend fun addTask(@Body task: TodoItem)

    @PUT("tasks/{id}")
    suspend fun updateTask(@Path("id") id: String, @Body task: TodoItem)

    @DELETE("tasks/{id}")
    suspend fun deleteTask(@Path("id") id: String)
}