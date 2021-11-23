package com.gregkluska.domain

import com.gregkluska.domain.model.User

interface INetworkDataSource {

    suspend fun getUsers(): List<User>

    suspend fun addUser(user: User): User

    suspend fun deleteUser(id: Long)

}