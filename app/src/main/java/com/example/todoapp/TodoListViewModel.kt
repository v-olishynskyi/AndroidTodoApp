package com.example.todoapp

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

data class Task(
    val id: Int,
    val name: String,
    val description: String?,
    val finished: Boolean,
    val isEditing: Boolean,
    val isNew: Boolean?
)

class TodoListViewModel : ViewModel() {
    private var _tasks = mutableStateListOf(
        Task(0, "Todo 1", "", false, isEditing = false, isNew = false),
        Task(1, "Todo 2", "", true, isEditing = false, isNew = false),
        Task(2, "Todo 3", "", false, isEditing = false, isNew = false),
        Task(3, "Todo 4", "", true, isEditing = false, isNew = false),
    )

    val tasks: List<Task> get() = _tasks


    fun onRemoveTask(taskId: Int) {
        val newTasks = _tasks.filter { it.id != taskId }
        _tasks.clear()
        _tasks.addAll(newTasks)
    }

    fun onAddTask() {
        val task = Task(
            id = tasks.size + 1,
            name = "",
            finished = false,
            isEditing = true,
            description = "",
            isNew = true
        )
        _tasks.add(0, task)
    }

    fun onTaskEditingStart(taskId: Int) {
        _tasks.forEachIndexed { index, task -> _tasks[index] = task.copy(isEditing = false) }
        val taskIndex = _tasks.indexOfFirst { it.id == taskId }
        _tasks[taskIndex] = _tasks[taskIndex].copy(isEditing = true)
    }

    fun onTaskEditingCancel(taskId: Int) {
        val taskIndex = _tasks.indexOfFirst { it.id == taskId }
        val task = _tasks[taskIndex]

        if (task.isNew == true) {
            onRemoveTask(taskId)
        } else {
            _tasks[taskIndex] = _tasks[taskIndex].copy(isEditing = false)
        }
    }

    fun onFinishedToggle(taskId: Int, value: Boolean) {
        val taskIndex = _tasks.indexOfFirst { it.id == taskId }
        _tasks[taskIndex] = _tasks[taskIndex].copy(finished = value)
    }

    fun onTaskEdited(taskId: Int, value: String) {
        val taskIndex = _tasks.indexOfFirst { it.id == taskId }
        _tasks[taskIndex] = _tasks[taskIndex].copy(name = value, isEditing = false)
    }

}