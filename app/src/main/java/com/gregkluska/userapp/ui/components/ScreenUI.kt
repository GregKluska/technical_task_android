package com.gregkluska.userapp.ui.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.gregkluska.userapp.R

@Composable
fun ScreenUI(
    scaffoldState: ScaffoldState,
    onActionClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
                actions = {
                    IconButton(onClick = onActionClick) {
                        Icon(imageVector = Icons.Default.Add, "Add")
                    }
                },
            )
        },
    ) {
        content()
    }
}