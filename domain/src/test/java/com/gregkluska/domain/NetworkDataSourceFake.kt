package com.gregkluska.domain

import com.gregkluska.domain.model.User

class NetworkDataSourceFake(
    private val responseType: ResponseType
): INetworkDataSource {

    private val db = users.toMutableList()

    override suspend fun getUsers(): List<User> {
        when(responseType) {
            ResponseType.Good -> return db
            ResponseType.Error -> throw Exception(EXCEPTION_USER_LIST)
        }
    }

    override suspend fun addUser(user: User): User {
        val nextId = users.maxByOrNull { it.id }?.id?.inc() ?: 1
        val userToAdd = user.copy(id = nextId)

        when(responseType) {
            ResponseType.Good -> {
                val res = db.add(userToAdd)

                if(res) return user else throw Exception(EXCEPTION_USER_ADD)
            }
            ResponseType.Error -> throw Exception(EXCEPTION_USER_ADD)
        }
    }

    override suspend fun deleteUser(id: Long) {
        if(responseType == ResponseType.Error) throw Exception(EXCEPTION_USER_DELETE)

        // It will throw when
        val res = db.removeIf { it.id == id }
        if(!res) throw Exception(EXCEPTION_USER_DELETE)
    }
}