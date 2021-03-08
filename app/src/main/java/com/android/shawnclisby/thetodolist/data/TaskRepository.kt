package com.android.shawnclisby.thetodolist.data

import android.app.Application
import com.android.shawnclisby.thetodolist.data.models.OrderBy
import com.android.shawnclisby.thetodolist.data.models.SortOrder
import com.android.shawnclisby.thetodolist.data.models.Task
import com.android.shawnclisby.thetodolist.data.room.TaskDao
import com.android.shawnclisby.thetodolist.data.room.TheListDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import kotlin.collections.ArrayList

class TaskRepository(application: Application) {

    private val database = TheListDatabase.getDatabase(application)
    private val taskDao: TaskDao = database.taskDao()

    fun getTasks(query: String, hideCompleted: Boolean, order: SortOrder): Flow<List<Task>> {
        return taskDao.getTasksFlow().map { tasks ->
            val filteredList = tasks.filter { task ->
                if (hideCompleted)
                    task.title.toLowerCase(Locale.getDefault())
                        .contains(query.toLowerCase(Locale.getDefault())) && !task.completed
                else task.title.toLowerCase(Locale.getDefault())
                    .contains(query.toLowerCase(Locale.getDefault()))
            }
            val sortedList = ArrayList<Task>()
            when (order) {
                is SortOrder.DateOrder -> {
                    if (order.orderBy == OrderBy.ASC) sortedList.addAll(ascendingByDate(filteredList))
                    else sortedList.addAll(descendingByDate(filteredList))
                }
                is SortOrder.TitleOrder -> {
                    if (order.orderBy == OrderBy.ASC) sortedList.addAll(
                        ascendingByTitle(
                            filteredList
                        )
                    )
                    else sortedList.addAll(descendingByTitle(filteredList))
                }
            }
            sortedList.toList()
        }
    }

    fun getTask(id: Int): Flow<Task> {
        return taskDao.getTask(id)
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

    private fun ascendingByTitle(tasks: List<Task>): List<Task> {
        return tasks.sortedBy { it.title }
    }

    private fun descendingByTitle(tasks: List<Task>): List<Task> {
        return tasks.sortedByDescending { it.title }
    }

    private fun ascendingByDate(tasks: List<Task>): List<Task> {
        return tasks.sortedBy { it.created }
    }

    private fun descendingByDate(tasks: List<Task>): List<Task> {
        return tasks.sortedByDescending { it.created }
    }
}