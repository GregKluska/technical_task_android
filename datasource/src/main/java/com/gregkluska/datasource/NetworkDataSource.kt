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
        val response = userService.getUsers(1)
        val pageCount = response.meta?.pagination?.pages

        // get last page
        val lastPage = userService.getUsers(pageCount ?: 1)

        return lastPage.data.map { it.toUser() }
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