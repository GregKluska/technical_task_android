package com.gregkluska.domain.state

sealed class UIComponent{

    data class Dialog(
        val title: String,
        val description: String,
    ): UIComponent()

    data class ConfirmDialog(
        val title: String,
        val description: String,
        val positiveLabel: String,
        val negativeLabel: String,
        val positiveAction: () -> Unit,
    ): UIComponent()
}
