package com.gregkluska.userapp.di

import com.gregkluska.domain.INetworkDataSource
import com.gregkluska.domain.interactors.AddUser
import com.gregkluska.domain.interactors.DeleteUser
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

    @ViewModelScoped
    @Provides
    fun provideAddUser(
        networkDataSource: INetworkDataSource
    ): AddUser {
        return AddUser(networkDataSource = networkDataSource)
    }

    @ViewModelScoped
    @Provides
    fun provideDeleteUser(
        networkDataSource: INetworkDataSource
    ): DeleteUser {
        return DeleteUser(networkDataSource = networkDataSource)
    }
}