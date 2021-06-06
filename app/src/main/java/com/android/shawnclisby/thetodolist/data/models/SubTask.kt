package com.android.shawnclisby.thetodolist.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "sub_tasks")
data class SubTask(
    @PrimaryKey(autoGenerate = true)
    val subtaskId: String = UUID.randomUUID().toString(),
    val subTaskCreatedById: String,
    val subTaskTitle: String
) : BaseTask(
    createdById = subTaskCreatedById,
    title = subTaskTitle,
    createdAt = System.currentTimeMillis(),
    priority = Priority.NONE,
    completed = false,
    description = null,
    dueDate = null,
    subtaskIds = null,
    progressPercentage = 0.0f,
    assignedToId = null
)