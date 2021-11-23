package com.gregkluska.domain.interactors

import com.gregkluska.domain.EXCEPTION_USER_LIST
import com.gregkluska.domain.NetworkDataSourceFake
import com.gregkluska.domain.Response
import com.gregkluska.domain.model.User
import com.gregkluska.domain.state.DataState
import com.gregkluska.domain.state.ProgressBarState
import com.gregkluska.domain.state.UIComponent
import com.gregkluska.domain.users
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.jetbrains.annotations.TestOnly
import org.junit.Test

class GetUsersTest {

    // system in test
    private lateinit var getUsers: GetUsers

    @Test
    fun `getUsers returns a list of users`() = runBlocking {
        val networkDataSource = NetworkDataSourceFake( response = Response.Good )

        getUsers = GetUsers(networkDataSource)

        val emissions = getUsers.execute().toList()

        // First emission should be loading
        assert(emissions[0] == DataState.Loading<List<User>>(ProgressBarState.Loading))

        // Second emission should be data
        assert(emissions[1] is DataState.Data)
        assert((emissions[1] as DataState.Data).data.size == users.size)

        // Confirm loading state is idle
        assert(emissions[2] == DataState.Loading<List<User>>(ProgressBarState.Idle))
    }

    @Test
    fun `getUsers receive error response`() = runBlocking {
        val networkDataSource = NetworkDataSourceFake( response = Response.Error )

        getUsers = GetUsers(networkDataSource)

        val emissions = getUsers.execute().toList()

        // First emission should be loading
        assert(emissions[0] == DataState.Loading<List<User>>(ProgressBarState.Loading))

        assert(emissions[1] is DataState.Response)
        assert((emissions[1] as DataState.Response).uiComponent is UIComponent.Dialog)
        assert(((emissions[1] as DataState.Response).uiComponent as UIComponent.Dialog).description == EXCEPTION_USER_LIST)

        // Confirm loading state is idle
        assert(emissions[2] == DataState.Loading<List<User>>(ProgressBarState.Idle))
    }

}