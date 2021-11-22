package com.gregkluska.userapp.ui.userlist

sealed class UserListEvent {

    object GetUsers: UserListEvent()

    object OpenModal: UserListEvent()

    data class DeleteUser(val id: Long): UserListEvent()

    data class AddUser(val id: Long): UserListEvent()

    object OnRemoveHeadFromQueue: UserListEvent()

}