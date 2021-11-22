package com.gregkluska.datasource

import com.gregkluska.datasource.model.toUser
import com.gregkluska.datasource.model.toUserDto
import com.gregkluska.domain.INetworkDataSource
import com.gregkluska.domain.model.User

class NetworkDataSource(
    private val userService: UserService
) : INetworkDataSource {

    override suspend fun getUsers(): List<User> {
        // get page count
        val pageCount = userService.getUsers().body()?.meta?.pagination?.pages ?: 1

        // get last page
        val lastPage = userService.getUsers(pageCount)

        val response = lastPage.body()?.data?.map { it.toUser() }

        return response ?: listOf()
    }

    override suspend fun addUser(user: User) {
        val response = userService.addUser(
            name = user.name,
            email = user.email,
            gender = user.gender,
            status = user.status
        )


    }

    override suspend fun deleteUser(id: Long) {
        userService.deleteUser(id)
    }


}