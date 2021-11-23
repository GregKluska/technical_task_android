package com.gregkluska.userapp.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gregkluska.domain.interactors.AddUser
import com.gregkluska.domain.interactors.DeleteUser
import com.gregkluska.domain.interactors.GetUsers
import com.gregkluska.domain.model.User
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
    private val getUsers: GetUsers,
    private val addUser: AddUser,
    private val deleteUser: DeleteUser
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
            is UserListEvent.AddUser -> addUser(event.name, event.email, event.gender)
            is UserListEvent.DeleteUser -> deleteUser(event.id)
            UserListEvent.GetUsers -> getUsers()
            UserListEvent.OpenModal -> showModal(true)
            UserListEvent.OnRemoveHeadFromQueue -> removeHeadMessage()
            is UserListEvent.LongPress -> deleteUserConfirmModal(event.id)
            UserListEvent.HideModal -> showModal(false)
        }
    }

    private fun addUser(name: String, email: String, gender: String) {
        addUser.execute(
            name = name,
            email = email,
            gender = gender
        ).onEach { dataState ->
            when(dataState) {
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
                is DataState.Data -> {
                    val newUserList = state.value.users.toMutableList()
                    newUserList.add(dataState.data)
                    state.value = state.value.copy(users = newUserList)
                }
                is DataState.Response -> {
                    appendToMessageQueue(dataState.uiComponent)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun showModal(value: Boolean) {
        state.value = state.value.copy(isAddUserModalVisible = value)
    }

    private fun deleteUser(id: Long) {
        deleteUser.execute(id).onEach { dataState ->
            when(dataState) {
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
                is DataState.Data -> {
                    val newUserList = state.value.users.toMutableList()
                    newUserList.removeAll { it.id == id }
                    state.value = state.value.copy(users = newUserList)
                }
                is DataState.Response -> {
                    appendToMessageQueue(dataState.uiComponent)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun deleteUserConfirmModal(id: Long) {
        appendToMessageQueue(
            UIComponent.ConfirmDialog(
                title = "Are you sure?",
                description = "Are you sure you want to remove this user?",
                positiveLabel = "OK",
                negativeLabel = "Cancel",
                positiveAction = { onTriggerEvent(UserListEvent.DeleteUser(id)) }
            )
        )
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