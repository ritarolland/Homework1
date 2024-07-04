package com.example.homework1.di

import com.example.homework1.data.TodoItemsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Provides
    @Singleton
    fun provideTodoItemsRepository(): TodoItemsRepository {
        return TodoItemsRepository()
    }

}