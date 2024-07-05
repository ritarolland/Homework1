package com.example.homework1.di

import android.content.Context
import androidx.room.Room
import com.example.homework1.data.TodoDatabase
import com.example.homework1.data.TodoItemsRepository
import com.example.homework1.data.TodoItemsRepositoryImpl
import com.example.homework1.domain.models.TodoItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TodoDatabase {
        return Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            "todo_database"
        ).build()
    }

    @Provides
    fun provideTodoItemDao(database: TodoDatabase): TodoItemDao {
        return database.todoItemDao()
    }

    @Provides
    fun provideTodoItemsRepository(todoItemDao: TodoItemDao): TodoItemsRepository {
        return TodoItemsRepositoryImpl(todoItemDao)
    }

}