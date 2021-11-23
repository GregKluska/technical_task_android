package com.gregkluska.domain.interactors

import com.gregkluska.domain.*
import com.gregkluska.domain.model.User
import com.gregkluska.domain.state.DataState
import com.gregkluska.domain.state.ProgressBarState
import com.gregkluska.domain.state.UIComponent
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

class DeleteUserTest {

    // system in test
    private lateinit var deleteUser: DeleteUser

    @Test
    fun `delete user should return data response`() = runBlocking {
        val networkDataSource = NetworkDataSourceFake( response = Response.Good )

        deleteUser = DeleteUser(networkDataSource)

        val emissions = deleteUser.execute(1).toList()

        // First emission should be loading
        assert(emissions[0] == DataState.Loading<List<User>>(ProgressBarState.Loading))

        // Second emission should be data
        assert(emissions[1] is DataState.Data)

        // Confirm loading state is idle
        assert(emissions[2] == DataState.Loading<List<User>>(ProgressBarState.Idle))
    }

    @Test
    fun `delete user, throws exception`() = runBlocking {
        val networkDataSource = NetworkDataSourceFake( response = Response.Error )

        deleteUser = DeleteUser(networkDataSource)

        val emissions2 = deleteUser.execute(1).toList()

        // First emission should be loading
        assert(emissions2[0] == DataState.Loading<List<User>>(ProgressBarState.Loading))

        assert(emissions2[1] is DataState.Response)
        assert((emissions2[1] as DataState.Response).uiComponent is UIComponent.Dialog)
        assert(((emissions2[1] as DataState.Response).uiComponent as UIComponent.Dialog).description == EXCEPTION_USER_DELETE)

        // Confirm loading state is idle
        assert(emissions2[2] == DataState.Loading<List<User>>(ProgressBarState.Idle))
    }

    @Test
    fun `delete user, then delete it again, should throw`() = runBlocking {
        val networkDataSource = NetworkDataSourceFake( response = Response.Good )

        deleteUser = DeleteUser(networkDataSource)

        val emissions = deleteUser.execute(1).toList()

        // First emission should be loading
        assert(emissions[0] == DataState.Loading<List<User>>(ProgressBarState.Loading))

        // Second emission should be data
        assert(emissions[1] is DataState.Data)

        // Confirm loading state is idle
        assert(emissions[2] == DataState.Loading<List<User>>(ProgressBarState.Idle))

        val emissions2 = deleteUser.execute(1).toList()

        // First emission should be loading
        assert(emissions2[0] == DataState.Loading<List<User>>(ProgressBarState.Loading))

        assert(emissions2[1] is DataState.Response)
        assert((emissions2[1] as DataState.Response).uiComponent is UIComponent.Dialog)
        assert(((emissions2[1] as DataState.Response).uiComponent as UIComponent.Dialog).description == EXCEPTION_USER_DELETE)

        // Confirm loading state is idle
        assert(emissions2[2] == DataState.Loading<List<User>>(ProgressBarState.Idle))
    }
}