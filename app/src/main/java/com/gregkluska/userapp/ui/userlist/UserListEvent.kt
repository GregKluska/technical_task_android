package com.gregkluska.userapp.ui.userlist

sealed class UserListEvent {

    object GetUsers: UserListEvent()

    object OpenModal: UserListEvent()
    object HideModal: UserListEvent()

    data class LongPress(val id: Long): UserListEvent()

    data class DeleteUser(val id: Long): UserListEvent()

    data class AddUser(
        val name: String,
        val email: String,
        val gender: String
    ): UserListEvent()

    object OnRemoveHeadFromQueue: UserListEvent()

}