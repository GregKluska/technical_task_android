package com.gregkluska.userapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gregkluska.domain.state.ProgressBarState
import com.gregkluska.domain.state.UIComponent
import com.gregkluska.userapp.ui.components.CircularIndeterminateProgressBar
import java.util.*

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    progressBarState: ProgressBarState,
    dialogQueue: Queue<UIComponent> = ArrayDeque(mutableListOf()),
    onRemoveHeadFromQueue: () -> Unit,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = if (darkTheme) {
            DarkColorPalette
        } else {
            LightColorPalette
        },
        typography = Typography,
        shapes = Shapes
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background)
        ) {
            Column {
                content()
            }

            if (progressBarState == ProgressBarState.Loading) {
                CircularIndeterminateProgressBar()
            }

            ProcessDialogQueue(
                dialogQueue = dialogQueue,
                onRemoveHeadFromQueue = onRemoveHeadFromQueue
            )
        }
    }
}

@Composable
fun ProcessDialogQueue(
    dialogQueue: Queue<UIComponent>,
    onRemoveHeadFromQueue: () -> Unit
) {
    if(dialogQueue.isNotEmpty()) {
        dialogQueue.peek()?.let { uiComponent ->
            when (uiComponent) {
                is UIComponent.Dialog -> {
                    AlertDialog(
                        onDismissRequest = {onRemoveHeadFromQueue()},
                        title = { Text(uiComponent.title) },
                        text = { Text(uiComponent.description) },
                        confirmButton = {
                            TextButton(
                                onClick = { onRemoveHeadFromQueue() },
                                content = { Text("OK") }
                            )
                        }

                    )
                }
                is UIComponent.ConfirmDialog -> {
                    AlertDialog(
                        onDismissRequest = {
                            onRemoveHeadFromQueue()
                        },
                        title = { Text(uiComponent.title) },
                        text = { Text(uiComponent.description) },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    uiComponent.positiveAction()
                                    onRemoveHeadFromQueue()
                                },
                                content = { Text(uiComponent.positiveLabel) }
                            )
                        },
                        dismissButton = {
                            TextButton(
                                onClick = { onRemoveHeadFromQueue() },
                                content = { Text(uiComponent.negativeLabel) }
                            )
                        }
                    )
                }
            }
        }
    }
}
