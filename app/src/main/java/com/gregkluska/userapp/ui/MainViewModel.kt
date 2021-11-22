package com.gregkluska.userapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gregkluska.domain.interactors.GetUsers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    init {
getUserss()
    }

    fun getUserss() {
        Log.d(TAG, "Hello hola: ")
        getUsers.execute().onEach {
            Log.d(TAG, "init: $it")
        }.launchIn(viewModelScope)
    }

}