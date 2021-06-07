package com.android.shawnclisby.thetodolist.data.local.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat
import java.util.*

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey
    val taskId: String = UUID.randomUUID().toString(),
    val taskTitle: String,
    val taskCategoryId: String,
    @Embedded(prefix = "task_created_member_") val createdBy: Member,
    @Embedded(prefix = "task_assigned_member_") val assignedTo: Member?
) : BaseTask(
    title = taskTitle,
    createdAt = System.currentTimeMillis(),
    priority = Priority.NONE,
    completed = false,
    description = null,
    dueDate = null
) {
    val dueDateFormat: String?
        get() = DateFormat.getDateInstance(DateFormat.MEDIUM).format(dueDate)
}
