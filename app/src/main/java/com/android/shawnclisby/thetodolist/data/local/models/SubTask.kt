package com.android.shawnclisby.thetodolist.data.local.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "sub_tasks")
data class SubTask(
    @PrimaryKey(autoGenerate = true)
    val subtaskId: String = UUID.randomUUID().toString(),
    val subTaskTitle: String,
    val parentTaskId: String,
    @Embedded val createdByMember: Member,
    @Embedded val assignedToMember: Member?
) : BaseTask(
    createdBy = createdByMember,
    title = subTaskTitle,
    createdAt = System.currentTimeMillis(),
    priority = Priority.NONE,
    completed = false,
    description = null,
    dueDate = null,
    assignedTo = assignedToMember
)