package com.gregkluska.userapp.di

import com.google.gson.GsonBuilder
import com.gregkluska.datasource.NetworkDataSource
import com.gregkluska.datasource.UserService
import com.gregkluska.domain.INetworkDataSource
import com.gregkluska.userapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideUserService(): UserService {
        // in local.properties add following line:
        // GOREST_TOKEN=ADD_YOUR_TOKEN_HERE

        val client = OkHttpClient.Builder().addInterceptor { chain ->
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer " + BuildConfig.GOREST_TOKEN)
                    .build()
            )
        }.build()

        return Retrofit.Builder()
            .client(client)
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