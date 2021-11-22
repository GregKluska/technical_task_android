package com.gregkluska.domain.state

sealed class ProgressBarState {

    object Idle: ProgressBarState()

    object Loading: ProgressBarState()

}