package com.samar.exam

import androidx.lifecycle.ViewModel

class TaskListViewModel : ViewModel() {

    private val taskRepository = TaskRepository.get()

    val  taskListLiveData = taskRepository.getTasks()
    val  todoListLiveData = taskRepository.getTodo()
    val inprogressListLiveData = taskRepository.getInpro()
    val doneListLiveData = taskRepository.getDone()

    fun addTask(task: Task) {
        taskRepository.addTask(task)
    }

    fun moveTask (task: Task){
        taskRepository.moveTask(task)
    }

    fun deleteTask(task: Task){
        taskRepository.deleteTask(task)
    }

}
