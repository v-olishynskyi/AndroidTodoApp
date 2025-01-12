package com.example.todoapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.todoapp.Task

@Composable
fun TodoItem(
    task: Task,
    onFinishedChange: (Int, Boolean) -> Unit,
    onTaskEdited: (Int, String) -> Unit,
    onTaskEditingBegin: (Int) -> Unit,
    onTaskEditingCancel: (Int) -> Unit,
    onRemoveTask: (Int) -> Unit,
    modifier: Modifier
) {
    val defaultTextFieldValue = TextFieldValue(
        text = task.name,
        selection = TextRange(task.name.length)
    )

    val taskName = remember {
        mutableStateOf(
            defaultTextFieldValue
        )
    }


    val hasValidationError = remember {
        derivedStateOf { taskName.value.text.isBlank() }
    }

    val expandedMenu = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    fun onEditingBegin() {
        expandedMenu.value = false
        onTaskEditingBegin(task.id)
    }

    fun onEditingCancel() {
        taskName.value =
            defaultTextFieldValue
        onTaskEditingCancel(task.id)
    }

    DisposableEffect(key1 = task.isEditing) {
        if (task.isEditing) {
            focusRequester.requestFocus()
        }

        onDispose { }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {
        if (task.isEditing) {
            IconButton(onClick = { onEditingCancel() }) {
                Icon(Icons.Outlined.Close, "Cancel")
            }
        } else {
            Checkbox(
                checked = task.finished,
                onCheckedChange = { onFinishedChange(task.id, it) },
            )
        }
        if (task.isEditing) {
            TextField(
                value = taskName.value, onValueChange = { taskName.value = it },
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .weight(1f)
                    .padding(8.dp),
                isError = hasValidationError.value,
                supportingText = {
                    if (hasValidationError.value) {
                        Text(text = "Incorrect value")
                    }
                }

            )
            IconButton(
                onClick = { onTaskEdited(task.id, taskName.value.text) },
                enabled = !hasValidationError.value
            ) {
                Icon(Icons.Outlined.Check, "Done")
            }
        } else {
            Text(
                text = taskName.value.text,
                Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                textDecoration = if (task.finished) TextDecoration.LineThrough else null,
            )

            IconButton(onClick = { expandedMenu.value = !expandedMenu.value }) {
                Icon(Icons.Outlined.MoreVert, "Options")

                DropdownMenu(
                    expanded = expandedMenu.value,
                    onDismissRequest = { expandedMenu.value = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Edit") },
                        onClick = ::onEditingBegin,
                        trailingIcon = { Icon(Icons.Outlined.Edit, "Edit") }
                    )
                    DropdownMenuItem(
                        text = { Text("Delete") },
                        onClick = { onRemoveTask(task.id) },
                        trailingIcon = { Icon(Icons.Outlined.Delete, "Delete") }
                    )
                }
            }


        }
    }
    Divider()

}

