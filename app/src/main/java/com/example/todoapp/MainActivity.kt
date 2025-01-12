package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import com.example.todoapp.ui.theme.TodoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoAppTheme {
                TodoAppEntryPoint()
            }
        }
    }
}

@Composable
fun TodoAppEntryPoint(viewModel: TodoListViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = viewModel::onAddTask,
                modifier = Modifier.onGloballyPositioned {
                    it.boundsInRoot().bottomRight
                }
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            TodoList()
        }
    }
}

@Preview()
@Composable
fun TodoAppEntryPointPreview() {
    TodoAppEntryPoint()
}