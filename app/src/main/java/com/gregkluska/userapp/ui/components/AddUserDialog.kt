package com.gregkluska.userapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.gregkluska.domain.model.User
import com.gregkluska.userapp.ui.util.isValidEmail
import java.util.*

@Composable
fun AddUserDialog(
    onDismissRequest: () -> Unit,
    onSubmit: (User) -> Unit
) {
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val gender = remember { mutableStateOf("female") }

    val isNameError = remember { mutableStateOf(false) }
    val isEmailError = remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = {
                    // Validation here
                    isNameError.value = name.value.isEmpty()
                    isEmailError.value = email.value.isEmpty() || !email.value.isValidEmail()

                    if (isNameError.value || isEmailError.value) {
                        // Do nothing
                    } else {
                        onSubmit(
                            User(
                                id = -1,
                                name = name.value,
                                email = email.value,
                                gender = gender.value,
                                status = "active"
                            )
                        )
                        onDismissRequest()
                    }
                },
                content = { Text("Create") }
            )
        },
        dismissButton = {
            TextButton(
                onClick = { onDismissRequest() },
                content = { Text("Cancel") }
            )
        },
        text = {
            Column(
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    style = MaterialTheme.typography.subtitle1,
                    text = "Fill the form and press Create to add user",
                    color = MaterialTheme.colors.contentColorFor(MaterialTheme.colors.surface)
                )


                // Username
                OutlinedTextField(
                    modifier = Modifier.padding(top = 16.dp),
                    label = { Text("Name") },
                    value = name.value,
                    isError = isNameError.value,
                    onValueChange = {
                        isNameError.value = it.isEmpty()
                        name.value = it

                    }
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    label = { Text("Email") },
                    value = email.value,
                    isError = isEmailError.value,
                    onValueChange = {
                        isEmailError.value = it.isEmpty() || !it.isValidEmail()
                        email.value = it
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))

                // Treemap Value -> Label(What user sees)
                val genders = TreeMap<String, String>()
                genders["male"] = "Male"
                genders["female"] = "Female"

                DropdownField(
                    label = { Text("Gender") },
                    onValueChange = { gender.value = it },
                    value = gender.value,
                    options = genders,
                    outlined = true
                )

            }

        }
    )
}