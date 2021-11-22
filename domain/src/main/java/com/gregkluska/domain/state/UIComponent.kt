package com.gregkluska.domain.state

sealed class UIComponent{

    data class Dialog(
        val title: String,
        val description: String,
    ): UIComponent()

}
