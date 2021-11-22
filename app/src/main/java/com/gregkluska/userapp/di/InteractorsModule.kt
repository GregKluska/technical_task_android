package com.gregkluska.userapp.di

import com.gregkluska.domain.INetworkDataSource
import com.gregkluska.domain.interactors.GetUsers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object InteractorsModule {

    @ViewModelScoped
    @Provides
    fun provideGetUsers(
        networkDataSource: INetworkDataSource
    ): GetUsers {
        return GetUsers(networkDataSource = networkDataSource)
    }
}