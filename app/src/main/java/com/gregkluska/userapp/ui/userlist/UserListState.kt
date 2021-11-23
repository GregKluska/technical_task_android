package com.gregkluska.userapp.ui.userlist

import com.gregkluska.domain.model.User
import com.gregkluska.domain.state.ProgressBarState
import com.gregkluska.domain.state.UIComponent
import java.util.*

data class UserListState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val users: List<User> = listOf(),
    val isAddUserModalVisible: Boolean = false,
    val messageQueue: Queue<UIComponent> = ArrayDeque(mutableListOf())
)