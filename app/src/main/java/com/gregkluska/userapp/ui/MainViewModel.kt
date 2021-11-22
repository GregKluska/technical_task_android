package com.gregkluska.userapp.ui

import androidx.lifecycle.ViewModel
import com.gregkluska.domain.interactors.GetUsers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val getUsers: GetUsers
) : ViewModel(){

    init {
        getUsers.execute()
    }

}