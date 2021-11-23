package com.gregkluska.userapp.ui.userlist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gregkluska.domain.model.User
import com.gregkluska.userapp.ui.components.AddUserDialog
import com.gregkluska.userapp.ui.components.ScreenUI

@ExperimentalFoundationApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserList(
    scaffoldState: ScaffoldState,
    event: (UserListEvent) -> Unit,
    state: UserListState
) {
    if(state.isAddUserModalVisible) {
        AddUserDialog(
            onDismissRequest = { event(UserListEvent.HideModal) },
            onSubmit = {
                event(
                    UserListEvent.AddUser(
                        name = it.name,
                        email = it.email,
                        gender = it.gender
                    )
                )
            }
        )
    }

    ScreenUI(
        scaffoldState = scaffoldState,
        onActionClick = { event(UserListEvent.OpenModal) }
    ) {
        UserList(
            users = state.users,
            onLongClick = { event(UserListEvent.LongPress(it)) }
        )
    }
}

@ExperimentalFoundationApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun UserList(
    users: List<User>,
    onLongClick: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier.background(MaterialTheme.colors.background)
    ) {

        items(users.size) { index ->
            val user = users[index]
            ListItem(
                modifier = Modifier.combinedClickable(
                    onClick = {},
                    onLongClick = { onLongClick(user.id) },
                    onDoubleClick = {}
                ),
                icon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "User"
                    )
                },
                secondaryText = { Text(user.email) },
                singleLineSecondaryText = true,
                overlineText = null,
                trailing = { Text(user.gender) },
                text = { Text(user.name) }
            )
            Divider()
        }
    }
}

@ExperimentalFoundationApi
@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
private fun UserListPreview() {
    val users = listOf(
        User(1, "Greg", "Email", "Male", "active"),
        User(1, "Greg", "Email", "Male", "active"),
        User(1, "Greg", "Email", "Male", "active"),
        User(1, "Greg", "Email", "Male", "active"),
        User(1, "Greg", "Email", "Male", "active"),
    )
    UserList(users) {}
}