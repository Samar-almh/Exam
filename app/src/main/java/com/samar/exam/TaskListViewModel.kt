package com.samar.exam

import androidx.lifecycle.ViewModel

class TaskListViewModel : ViewModel() {
    private val taskRepository = TaskRepository.get()
    val  taskListLiveData = taskRepository.getTasks()

    fun addTask(task: Task) {
        taskRepository.addTask(task)
    }

}
