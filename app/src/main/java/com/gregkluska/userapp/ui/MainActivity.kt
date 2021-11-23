package com.gregkluska.userapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.rememberScaffoldState
import com.gregkluska.userapp.ui.theme.AppTheme
import com.gregkluska.userapp.ui.userlist.UserList
import com.gregkluska.userapp.ui.userlist.UserListEvent
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val scaffoldState = rememberScaffoldState()

            AppTheme(
                progressBarState = viewModel.state.value.progressBarState,
                dialogQueue = viewModel.state.value.messageQueue,
                onRemoveHeadFromQueue = { viewModel.onTriggerEvent(UserListEvent.OnRemoveHeadFromQueue) }
            ) {
                UserList(
                    state = viewModel.state.value,
                    event = viewModel::onTriggerEvent,
                    scaffoldState = scaffoldState
                )
            }
        }
    }
}