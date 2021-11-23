package com.gregkluska.domain.interactors

import com.gregkluska.domain.INetworkDataSource
import com.gregkluska.domain.state.DataState
import com.gregkluska.domain.state.ProgressBarState
import com.gregkluska.domain.state.UIComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteUser(
    private val networkDataSource: INetworkDataSource
) {

    fun execute(id: Long): Flow<DataState<Boolean>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
            networkDataSource.deleteUser(id)
            emit(DataState.Data(true))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                DataState.Response(
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