package com.gregkluska.datasource

import com.gregkluska.datasource.model.toUser
import com.gregkluska.domain.model.User
import kotlinx.coroutines.runBlocking
import org.junit.Test

class NetworkDataSourceTest {

    // system in test
    private lateinit var networkDataSource: NetworkDataSource

    @Test
    fun `get users returns last page results`() = runBlocking {
        // setup
        val userService: UserService = UserServiceFake(responseType = ResponseType.Good)

        networkDataSource = NetworkDataSource(userService = userService)

        val response = networkDataSource.getUsers()

        assert(response == users.map { it.toUser() })
    }

    @Test(expected = Exception::class)
    fun `get users throws exception`() = runBlocking {
        // setup
        val userService: UserService = UserServiceFake(responseType = ResponseType.NetworkError)

        networkDataSource = NetworkDataSource(userService)

        val res = networkDataSource.getUsers()
    }

    @Test
    fun `add user and check if is in the list`() = runBlocking {
        val userService: UserService = UserServiceFake(responseType = ResponseType.Good)
        networkDataSource = NetworkDataSource(userService)

        val user = User(
            id = -1,
            name = "Greg",
            email = "test@test.com",
            gender = "male",
            status = "active"
        )

        val res = networkDataSource.addUser(user)
        // Is returned user the same (skip id)
        assert(user.copy(id = res.id) == res)

        val users = networkDataSource.getUsers()
        assert(user.copy(id = res.id) in users)


    }

    @Test(expected = Exception::class)
    fun `add user twice (the same email) and expect to throw`() = runBlocking {
        val userService: UserService = UserServiceFake(responseType = ResponseType.Good)
        networkDataSource = NetworkDataSource(userService)

        val user = User(
            id = -1,
            name = "Greg",
            email = "test@test.com",
            gender = "male",
            status = "active"
        )

        val res = networkDataSource.addUser(user)
        val res2 = networkDataSource.addUser(user)
    }

    @Test(expected = Exception::class)
    fun `add user error response`() = runBlocking {
        val userService: UserService = UserServiceFake(responseType = ResponseType.Error)
        networkDataSource = NetworkDataSource(userService)

        val user = User(
            id = -1,
            name = "Greg",
            email = "test@test.com",
            gender = "male",
            status = "active"
        )

        val res = networkDataSource.addUser(user)
    }

    @Test
    fun `delete user and check if is in the list`() = runBlocking {
        val userService: UserService = UserServiceFake(responseType = ResponseType.Good)
        networkDataSource = NetworkDataSource(userService)

        val res = networkDataSource.deleteUser(1)

        val res1 = networkDataSource.getUsers()
        // Assert there's no user with id 1L
        assert(res1.find { it.id == 1L } == null)

    }

    @Test(expected = Exception::class)
    fun `delete user twice (the same id) and expect to throw`() = runBlocking {
        val userService: UserService = UserServiceFake(responseType = ResponseType.Good)
        networkDataSource = NetworkDataSource(userService)


        val res = networkDataSource.deleteUser(1L)
        val res2 = networkDataSource.deleteUser(1L)
    }

    @Test(expected = Exception::class)
    fun `delete user error response`() = runBlocking {
        val userService: UserService = UserServiceFake(responseType = ResponseType.Error)
        networkDataSource = NetworkDataSource(userService)

        val res = networkDataSource.deleteUser(1L)
    }
}