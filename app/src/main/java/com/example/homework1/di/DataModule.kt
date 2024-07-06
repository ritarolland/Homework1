package com.example.homework1.di

import android.content.ContentResolver
import android.content.Context
import androidx.room.Room
import com.example.homework1.ApiService
import com.example.homework1.AuthInterceptor
import com.example.homework1.CustomHostnameVerifier
import com.example.homework1.LastKnownRevisionInterceptor
import com.example.homework1.ServerTodoItemMapper
import com.example.homework1.data.DeviceNameRepository
import com.example.homework1.data.LastKnownRevisionRepository
import com.example.homework1.data.TodoDatabase
import com.example.homework1.data.TodoItemsRepository
import com.example.homework1.data.TodoItemsRepositoryImpl
import com.example.homework1.domain.models.TodoItemDao
import com.example.homework1.utils.NetworkChecker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import retrofit2.converter.scalars.ScalarsConverterFactory

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

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
    @Singleton
    fun provideNetworkChecker(@ApplicationContext context: Context): NetworkChecker {
        return NetworkChecker(context)
    }
    @Provides
    fun provideTodoItemDao(database: TodoDatabase): TodoItemDao {
        return database.todoItemDao()
    }

    @Provides
    fun provideTodoItemsRepository(
        todoItemDao: TodoItemDao,
        todoApiService: ApiService,
        networkChecker: NetworkChecker,
        serverTodoItemMapper: ServerTodoItemMapper,
        lastKnownRevisionRepository: LastKnownRevisionRepository
    ): TodoItemsRepository {
        return TodoItemsRepositoryImpl(
            todoItemDao,
            todoApiService,
            networkChecker,
            serverTodoItemMapper,
            lastKnownRevisionRepository
        )
    }

    @Provides
    fun provideContentResolver(@ApplicationContext context: Context): ContentResolver {
        return context.contentResolver
    }

    @Provides
    fun provideDeviceNameRepository(contentResolver: ContentResolver): DeviceNameRepository {
        return DeviceNameRepository(contentResolver)
    }

    @Provides
    fun provideLastKnownRevisionRepository(): LastKnownRevisionRepository {
        return LastKnownRevisionRepository()
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(): AuthInterceptor {
        //val token = "Cerin"
        return AuthInterceptor()
    }



    @Provides
    @Singleton
    fun provideLastRevisionInterceptor(lastKnownRevisionRepository: LastKnownRevisionRepository): LastKnownRevisionInterceptor {
        //val token = "Cerin"
        return LastKnownRevisionInterceptor(lastKnownRevisionRepository)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor, lastKnownRevisionInterceptor: LastKnownRevisionInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(lastKnownRevisionInterceptor)
            .hostnameVerifier(CustomHostnameVerifier("beta.mrdekk.ru"))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://beta.mrdekk.ru/todo/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideTodoApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideServerTodoItemMapper(deviceNameRepository: DeviceNameRepository): ServerTodoItemMapper {
        return ServerTodoItemMapper(deviceNameRepository)
    }

}