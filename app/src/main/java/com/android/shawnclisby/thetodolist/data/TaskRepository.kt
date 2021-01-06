package com.android.shawnclisby.thetodolist.data

import android.app.Application
import com.android.shawnclisby.thetodolist.data.models.Task
import com.android.shawnclisby.thetodolist.data.room.TaskDao
import com.android.shawnclisby.thetodolist.data.room.TheListDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepository(application: Application) {

    private val database = TheListDatabase.getDatabase(application)
    private val taskDao: TaskDao = database.taskDao()

    fun getTasks(query: String, hideCompleted: Boolean): Flow<List<Task>> {
        return taskDao.getTasksFlow().map { tasks ->
            tasks.filter { task ->
                if (hideCompleted)
                task.title.toLowerCase().contains(query.toLowerCase()) && !task.completed
                else task.title.toLowerCase().contains(query.toLowerCase())
            }
        }
    }

    suspend fun insert(task: Task) {
        taskDao.insertTask(task)
    }

    suspend fun update(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun delete(task: Task) {
        taskDao.deleteTask(task)
    }
}