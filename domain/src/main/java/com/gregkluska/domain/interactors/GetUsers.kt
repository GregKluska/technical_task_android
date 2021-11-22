package com.gregkluska.domain.interactors

import com.gregkluska.domain.INetworkDataSource
import com.gregkluska.domain.model.User
import com.gregkluska.domain.state.DataState
import com.gregkluska.domain.state.ProgressBarState
import com.gregkluska.domain.state.UIComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUsers(
    val networkDataSource: INetworkDataSource
) {

    fun execute(): Flow<DataState<List<User>>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
            val users: List<User> = networkDataSource.getUsers()
            emit(DataState.Data(users))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                DataState.Response<List<User>>(
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