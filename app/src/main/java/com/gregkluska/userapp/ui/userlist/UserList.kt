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
import com.gregkluska.domain.model.User
import com.gregkluska.userapp.ui.components.ScreenUI

@ExperimentalFoundationApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserList(
    scaffoldState: ScaffoldState,
    event: (UserListEvent) -> Unit,
    users: List<User>
) {
    ScreenUI(
        scaffoldState = scaffoldState,
        onActionClick = { event(UserListEvent.OpenModal) }
    ) {
        LazyColumn(
            modifier = Modifier.background(MaterialTheme.colors.background)
        ) {

            items(users.size) { index ->
                val user = users[index]
                ListItem(
                    modifier = Modifier.combinedClickable(
                        onClick = {},
                        onLongClick = { event(UserListEvent.LongPress(user.id)) },
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

}