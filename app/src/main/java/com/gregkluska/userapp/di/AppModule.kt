package com.gregkluska.userapp.di

import android.net.Network
import com.google.gson.GsonBuilder
import com.gregkluska.datasource.NetworkDataSource
import com.gregkluska.datasource.UserService
import com.gregkluska.domain.INetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideUserService(): UserService {
        return Retrofit.Builder()
            .baseUrl("https://gorest.co.in/public/v1/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(UserService::class.java)
    }

    @Singleton
    @Provides
    fun provideNetworkDataSource(
        userService: UserService
    ): INetworkDataSource {
        return NetworkDataSource(
            userService = userService
        )
    }

}