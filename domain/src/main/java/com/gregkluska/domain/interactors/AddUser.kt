package com.gregkluska.domain.interactors

import com.gregkluska.domain.INetworkDataSource
import com.gregkluska.domain.model.User
import com.gregkluska.domain.state.DataState
import com.gregkluska.domain.state.ProgressBarState
import com.gregkluska.domain.state.UIComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddUser(
    private val networkDataSource: INetworkDataSource
) {

    fun execute(name: String, email: String, gender: String): Flow<DataState<User>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
            val userToAdd = User(
                id = -1,
                name = name,
                email = email,
                gender = gender,
                status = "active"
            )

            val addedUser: User = networkDataSource.addUser(user = userToAdd)
            emit(DataState.Data(addedUser))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                DataState.Response<User>(
                    uiComponent = UIComponent.Dialog(
                        title = "Error",
                        description = e.message ?: "Unknown error"
                    )
                )
            )
        } finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }

}