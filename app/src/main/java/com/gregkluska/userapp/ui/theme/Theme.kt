package com.gregkluska.userapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gregkluska.domain.state.ProgressBarState
import com.gregkluska.userapp.R
import com.gregkluska.userapp.ui.components.CircularIndeterminateProgressBar
import com.gregkluska.userapp.ui.components.GenericDialog
import com.gregkluska.userapp.ui.components.GenericDialogInfo
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
    dialogQueue: Queue<GenericDialogInfo>? = null,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = if (darkTheme) { DarkColorPalette } else { LightColorPalette },
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

            if(progressBarState == ProgressBarState.Loading) {
                CircularIndeterminateProgressBar()
            }

            ProcessDialogQueue(
                dialogQueue = dialogQueue,
            )
        }
    }
}

@Composable
fun ProcessDialogQueue(
    dialogQueue: Queue<GenericDialogInfo>?,
) {
    dialogQueue?.peek()?.let { dialogInfo ->
        GenericDialog(
            onDismiss = dialogInfo.onDismiss,
            title = dialogInfo.title,
            description = dialogInfo.description,
            positiveAction = dialogInfo.positiveAction,
            negativeAction = dialogInfo.negativeAction
        )
    }
}
