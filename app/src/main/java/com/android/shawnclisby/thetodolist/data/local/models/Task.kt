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
    val taskCreatedById: String,
    val taskTitle: String,
    val taskCategoryId: String,
    @Embedded val createdByMember: Member,
    @Embedded val assignedToMember: Member?
) : BaseTask(
    createdBy = createdByMember,
    title = taskTitle,
    createdAt = System.currentTimeMillis(),
    priority = Priority.NONE,
    completed = false,
    description = null,
    dueDate = null,
    assignedTo = assignedToMember
) {
    val dueDateFormat: String?
        get() = DateFormat.getDateInstance(DateFormat.MEDIUM).format(dueDate)
}
