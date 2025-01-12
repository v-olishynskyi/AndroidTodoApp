package com.example.todoapp

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todoapp.components.TodoItem

@Composable
fun TodoList(
    modifier: Modifier = Modifier,
    viewModel: TodoListViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val tasks = viewModel.tasks

    LazyColumn(
        Modifier
            .padding(vertical = 16.dp)
//            .background(Color.Yellow)
    ) {
        items(items = tasks, key = { it.id }) {
            TodoItem(
                task = it,
                onRemoveTask = viewModel::onRemoveTask,
                onFinishedChange = viewModel::onFinishedToggle,
                onTaskEdited = viewModel::onTaskEdited,
                onTaskEditingBegin = viewModel::onTaskEditingStart,
                onTaskEditingCancel = viewModel::onTaskEditingCancel,
                modifier = modifier
            )
        }
    }


}