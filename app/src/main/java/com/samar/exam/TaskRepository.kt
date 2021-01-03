package com.samar.exam

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import database.TaskDatabase
import database.migration_1_2
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "task-database"

class TaskRepository private constructor(context: Context) {

    private val database : TaskDatabase = Room.databaseBuilder(context.applicationContext,
        TaskDatabase::class.java, DATABASE_NAME)
        .addMigrations(migration_1_2)
        .build()

    private val taskDao = database.taskDao()
    private val executor = Executors.newSingleThreadExecutor()
    fun getTasks(): LiveData<List<Task>> = taskDao.getTasks()
    fun getTodo(): LiveData<List<Task>> = taskDao.getTodo("To-Do")
    fun getInpro(): LiveData<List<Task>> = taskDao.getInpro("In-Progress")
    fun getDone(): LiveData<List<Task>> = taskDao.getDone("Done")

    fun getTask(id: UUID): LiveData<Task?> = taskDao.getTask(id)


    fun addTask(task: Task) {
        executor.execute {
            taskDao.addTask(task)
        }
    }

    fun moveTask(task: Task){
        executor.execute {
            taskDao.moveTask(task)
        }
    }

    fun deleteTask(task: Task) {
        executor.execute {
            taskDao.deleteTask(task)
        }  }


    companion object {
        private var INSTANCE: TaskRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = TaskRepository(context)
            }
        }
        fun get(): TaskRepository {
            return INSTANCE ?:
            throw IllegalStateException("TaskRepository must be initialized")
        }
    }
}