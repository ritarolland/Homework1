package com.example.homework1.di

import android.content.Context
import com.example.homework1.ApiService
import com.example.homework1.AuthInterceptor
import com.example.homework1.LastKnownRevisionInterceptor
import com.example.homework1.data.LastKnownRevisionRepository
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

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {




}