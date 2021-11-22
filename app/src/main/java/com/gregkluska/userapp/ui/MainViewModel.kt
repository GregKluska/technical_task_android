package com.gregkluska.userapp.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gregkluska.domain.interactors.GetUsers
import com.gregkluska.domain.state.DataState
import com.gregkluska.domain.state.UIComponent
import com.gregkluska.userapp.ui.userlist.UserListEvent
import com.gregkluska.userapp.ui.userlist.UserListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val getUsers: GetUsers
) : ViewModel(){

    companion object {
        private const val TAG = "MainViewModel"
    }

    val state: MutableState<UserListState> = mutableStateOf(UserListState())

    init {
        onTriggerEvent(UserListEvent.GetUsers)
    }

    fun onTriggerEvent(event: UserListEvent) {
        when(event) {
            is UserListEvent.AddUser -> TODO()
            is UserListEvent.DeleteUser -> TODO()
            UserListEvent.GetUsers -> getUsers()
            UserListEvent.OpenModal -> TODO()
            UserListEvent.OnRemoveHeadFromQueue -> removeHeadMessage()
            is UserListEvent.LongPress -> TODO()
        }
    }

    private fun getUsers() {
        getUsers.execute().onEach { dataState ->
            when(dataState) {
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
                is DataState.Data -> {
                    state.value = state.value.copy(users = dataState.data)
                }
                is DataState.Response -> {
                    appendToMessageQueue(dataState.uiComponent)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun appendToMessageQueue(uiComponent: UIComponent) {
        val queue = state.value.messageQueue
        queue.add(uiComponent)
        state.value = state.value.copy(messageQueue = ArrayDeque(mutableListOf()))
        state.value = state.value.copy(messageQueue = queue)
    }

    private fun removeHeadMessage() {
        try {
            val queue = state.value.messageQueue
            queue.remove()
            state.value = state.value.copy(messageQueue = ArrayDeque(mutableListOf()))
            state.value = state.value.copy(messageQueue = queue)
        } catch (e: Exception) {
            // Nothing to remove from DialogQueue
        }
    }

}