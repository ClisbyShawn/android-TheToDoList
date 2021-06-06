package com.android.shawnclisby.thetodolist.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat
import java.util.*

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey
    val taskId: String = UUID.randomUUID().toString(),
    val taskCreatedById: String,
    val taskTitle: String,
) : BaseTask(
    createdById = taskCreatedById,
    title = taskTitle,
    createdAt = System.currentTimeMillis(),
    priority = Priority.NONE,
    completed = false,
    description = null,
    dueDate = null,
    subtaskIds = null,
    progressPercentage = 0.0f,
    assignedToId = null
) {
    val dueDateFormat: String
        get() = DateFormat.getDateInstance(DateFormat.MEDIUM).format(dueDate) ?: "No Due Date"
}
