package com.android.shawnclisby.thetodolist.data.local.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "sub_tasks")
data class SubTask(
    @PrimaryKey
    val subtaskId: String = UUID.randomUUID().toString(),
    val subTaskTitle: String,
    val parentTaskId: String,
    @Embedded(prefix = "sub_created_member_") val createdBy: Member,
    @Embedded(prefix = "sub_assigned_member_") val assignedTo: Member?
) : BaseTask(
    title = subTaskTitle,
    createdAt = System.currentTimeMillis(),
    priority = Priority.NONE,
    completed = false,
    description = null,
    dueDate = null
)