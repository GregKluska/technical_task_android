package com.gregkluska.userapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gregkluska.userapp.ui.util.Border
import com.gregkluska.userapp.ui.util.border
import java.util.*

@Composable
fun <T> DropdownField(
    label: @Composable () -> Unit,
    onValueChange: (T) -> Unit,
    value: T,
    options: TreeMap<T, String>,
    isError: Boolean = false,
    outlined: Boolean = false
) {
    var expanded by remember { mutableStateOf(false) }

    val unfocusedIndicatorLineOpacity = TextFieldDefaults.UnfocusedIndicatorLineOpacity

    val errorColor = MaterialTheme.colors.error
    val textColor = LocalContentColor.current.copy(LocalContentAlpha.current)
    val iconColor = MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.IconOpacity)
    val placeholderColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)
    val activePlaceholderColor = MaterialTheme.colors.primary.copy(alpha = ContentAlpha.high)
    val indicatorColor = MaterialTheme.colors.onSurface.copy(alpha = unfocusedIndicatorLineOpacity)

    val colors = TextFieldDefaults.textFieldColors(
        disabledTextColor = textColor,
        disabledLeadingIconColor = iconColor,
        disabledTrailingIconColor = iconColor,
        disabledPlaceholderColor = placeholderColor,
        disabledLabelColor = if (isError) errorColor else if (expanded) activePlaceholderColor else placeholderColor,
        disabledIndicatorColor = if (isError) errorColor else if (expanded) activePlaceholderColor else indicatorColor
    )

    val strokeWidth: Dp = if (expanded && !outlined) 2.dp else 0.dp

    val icon = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown

    val modifier = Modifier
        .clickable { expanded = !expanded }
        .border(
            bottom = Border(
                strokeWidth = strokeWidth,
                color = activePlaceholderColor
            )
        )

    Box(
        modifier = Modifier.wrapContentWidth()
    ) {
        if(outlined) {
            OutlinedTextField(
                label = label,
                value = options.getOrDefault(value, ""),
                enabled = false,
                onValueChange = { },
                modifier = modifier,
                trailingIcon = { Icon(icon, "contentDescription") },
                colors = colors
            )
        } else {
            TextField(
                label = label,
                value = options.getOrDefault(value, ""),
                enabled = false,
                onValueChange = { },
                modifier = modifier,
                trailingIcon = { Icon(icon, "contentDescription") },
                colors = colors
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            options.forEach {
                DropdownMenuItem(onClick = {
                    expanded = false
                    onValueChange(it.key)
                }) {
                    Text(text = it.value)
                }
            }
        }

    }

}