package com.gregkluska.domain.interactors

import com.gregkluska.domain.*
import com.gregkluska.domain.model.User
import com.gregkluska.domain.state.DataState
import com.gregkluska.domain.state.ProgressBarState
import com.gregkluska.domain.state.UIComponent
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

class AddUserTest {

    // system in test
    private lateinit var addUser: AddUser

    @Test
    fun `getUsers returns a list of users`() = runBlocking {
        val networkDataSource = NetworkDataSourceFake( response = Response.Good )

        addUser = AddUser(networkDataSource)

        val emissions = addUser.execute(
            name = users[0].name,
            email = users[0].email,
            gender = users[0].gender
        ).toList()

        // First emission should be loading
        assert(emissions[0] == DataState.Loading<List<User>>(ProgressBarState.Loading))

        // Second emission should be data
        assert(emissions[1] is DataState.Data)

        // Confirm loading state is idle
        assert(emissions[2] == DataState.Loading<List<User>>(ProgressBarState.Idle))
    }

    @Test
    fun `getUsers receive error response`() = runBlocking {
        val networkDataSource = NetworkDataSourceFake( response = Response.Error )

        addUser = AddUser(networkDataSource)

        val emissions = addUser.execute(
            name = users[0].name,
            email = users[0].email,
            gender = users[0].gender
        ).toList()

        // First emission should be loading
        assert(emissions[0] == DataState.Loading<List<User>>(ProgressBarState.Loading))

        assert(emissions[1] is DataState.Response)
        assert((emissions[1] as DataState.Response).uiComponent is UIComponent.Dialog)
        assert(((emissions[1] as DataState.Response).uiComponent as UIComponent.Dialog).description == EXCEPTION_USER_ADD)

        // Confirm loading state is idle
        assert(emissions[2] == DataState.Loading<List<User>>(ProgressBarState.Idle))
    }

}