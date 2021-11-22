package com.gregkluska.userapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gregkluska.domain.model.User

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserList(
    users: List<User>
) {
    LazyColumn(
        modifier = Modifier.background(MaterialTheme.colors.background)
    ) {

        items(users.size) { index ->
            val user = users[index]
            ListItem(
                icon = { Icon(imageVector = Icons.Default.Person, contentDescription = "User") },
                secondaryText = { Text( user.email ) },
                singleLineSecondaryText = true,
                overlineText = null,
                trailing = {Text(user.gender)},
                text = { Text( user.name ) }
            )
            Divider()
        }
    }
}